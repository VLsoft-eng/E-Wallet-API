package ru.cft.template.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.cft.template.api.enumeration.TransferType;
import ru.cft.template.api.model.transfer.CreateTransferRequest;
import ru.cft.template.api.model.transfer.GetTransferListRequestParams;
import ru.cft.template.api.model.transfer.TransferDto;
import ru.cft.template.api.model.transfer.TransferListDto;
import ru.cft.template.core.entity.Transfer;
import ru.cft.template.core.entity.Wallet;
import ru.cft.template.core.enumeration.TransferStatus;
import ru.cft.template.core.exception.*;
import ru.cft.template.core.mapper.TransferMapper;
import ru.cft.template.core.repository.TransferRepository;
import ru.cft.template.core.security.userDetails.CustomUserDetails;
import ru.cft.template.core.service.TransferService;
import ru.cft.template.core.service.WalletService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final WalletService walletService;
    private final TransferMapper transferMapper;

    public TransferDto createTransfer(CreateTransferRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("Creating transfer request from user {} to {}", userDetails.getId(), request.receiverCredential());

        Wallet receiverWallet = walletService.getWalletByCredential(request.receiverCredential())
                .orElseThrow(() -> {
                    log.error("Receiver wallet not found for credential: {}", request.receiverCredential());
                    return new WalletNotFoundException();
                });

        Wallet senderWallet = walletService.getWalletByUserId(userDetails.getId())
                .orElseThrow(() -> {
                    log.error("Sender wallet not found for user: {}", userDetails.getId());
                    return new WalletNotFoundException();
                });

        if (receiverWallet.getUser().getId().equals(userDetails.getId())) {
            log.error("User {} attempted to transfer money to themselves", userDetails.getId());
            throw new SelfTransferException();
        }

        if (senderWallet.getBalance() < request.amount()) {
            log.warn("Insufficient funds: user {} has balance {}, but tried to send {}",
                    userDetails.getId(), senderWallet.getBalance(), request.amount());
            throw new BalanceLessThanTransferException();
        }

        walletService.incrementMoney(receiverWallet, request.amount());
        walletService.decrementMoney(senderWallet, request.amount());

        Transfer transfer = Transfer.builder()
                .senderWallet(senderWallet)
                .receiverWallet(receiverWallet)
                .amount(request.amount())
                .transferStatus(TransferStatus.PAID)
                .build();

        transferRepository.save(transfer);
        log.info("Transfer successfully created: {} -> {} | Amount: {}", senderWallet.getId(), receiverWallet.getId(), request.amount());

        return transferMapper.toTransferDto(transfer);
    }

    public TransferDto getTransfer(UUID transferId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("Retrieving transfer {} for user {}", transferId, userDetails.getId());

        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> {
                    log.error("Transfer {} not found", transferId);
                    return new TransferNotFoundException();
                });

        Wallet receiverWallet = transfer.getReceiverWallet();
        Wallet senderWallet = transfer.getSenderWallet();

        boolean isCurrentUserReceiver = receiverWallet.getUser().getId().equals(userDetails.getId());
        boolean isCurrentUserSender = senderWallet.getUser().getId().equals(userDetails.getId());

        if (!(isCurrentUserReceiver || isCurrentUserSender)) {
            log.error("User {} tried to access transfer {} without permission", userDetails.getId(), transferId);
            throw new DoesntHaveRightsException(ExceptionMessage.GET_OTHER_TRANSFER_NOT_ALLOWED);
        }

        log.info("Transfer {} retrieved successfully for user {}", transferId, userDetails.getId());
        return transferMapper.toTransferDto(transfer);
    }

    public TransferListDto getTransferList(GetTransferListRequestParams params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("Fetching transfer list for user {} with status {}", userDetails.getId(), params.status());

        if (params.receiverId() != null) {
            return getTransferListWithReceiverId(params, userDetails.getId());
        }

        List<Transfer> transfers = transferRepository.findAllByParams(userDetails.getId(), params.status());
        List<Transfer> filteredTransfers = filterTransfersByUserStatus(transfers, params.type(), userDetails.getId());

        return transferMapper.toTransferListDto(filteredTransfers);
    }

    private TransferListDto getTransferListWithReceiverId(GetTransferListRequestParams params, UUID userId) {
        log.info("Start fetching transfers where user {} and receiver {} are both involved", userId, params.receiverId());

        List<Transfer> transfers = transferRepository.findAllByParamsAndCollaborate(userId, params.receiverId(), params.status());
        List<Transfer> filteredTransfers = filterTransfersByUserStatus(transfers, params.type(), userId);

        return transferMapper.toTransferListDto(filteredTransfers);
    }

    private List<Transfer> filterTransfersByUserStatus(List<Transfer> transfers, TransferType type, UUID userId) {
        Stream<Transfer> transfersStream = transfers.stream();

        if (type.equals(TransferType.IN)) {
            transfersStream = transfersStream.filter(t -> t.getReceiverWallet().getUser().getId().equals(userId));
        } else if (type.equals(TransferType.OUT)) {
            transfersStream = transfersStream.filter(t -> t.getSenderWallet().getUser().getId().equals(userId));
        }

        return transfersStream.toList();
    }
}

