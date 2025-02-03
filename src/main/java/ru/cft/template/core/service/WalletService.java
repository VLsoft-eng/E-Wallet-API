package ru.cft.template.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.cft.template.api.model.wallet.WalletDto;
import ru.cft.template.core.entity.Wallet;
import ru.cft.template.core.exception.DoesntHaveRightsException;
import ru.cft.template.core.exception.ExceptionMessage;
import ru.cft.template.core.exception.WalletNotFoundException;
import ru.cft.template.core.mapper.WalletMapper;
import ru.cft.template.core.repository.WalletRepository;
import ru.cft.template.core.security.userDetails.CustomUserDetails;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final SecureRandom secureRandom;

    public WalletDto getWallet(UUID walletId) {
        log.info("Getting wallet with id={} started", walletId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Wallet wallet = walletRepository.findById(walletId).orElseThrow(WalletNotFoundException::new);

        if (!wallet.getUser().getId().equals(userDetails.getId())) {
            log.error("Get wallet request from user with id={} rejected", userDetails.getId());
            throw new DoesntHaveRightsException(ExceptionMessage.GETTING_OTHER_WALLET_NOT_ALLOWED);
        }

        log.info("User with id={} get wallet", userDetails.getId());
        return walletMapper.toWalletDto(wallet);
    }

    public WalletDto hesoyam(UUID walletId) {
        log.info("Hesoyam wallet with id={} started", walletId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(WalletNotFoundException::new);

        if (!wallet.getUser().getId().equals(userDetails.getId())) {
            log.error("Hesoyam request from user with id={} rejected", userDetails.getId());
            throw new DoesntHaveRightsException(ExceptionMessage.GETTING_OTHER_WALLET_NOT_ALLOWED);
        }

        double chance = secureRandom.nextDouble();
        if (chance < 0.25) {
            log.error("Hesoyam request from user with id={} increment money", userDetails.getId());
            incrementMoney(wallet, 100L);
        }

        return walletMapper.toWalletDto(wallet);
    }

    public void incrementMoney(Wallet wallet, Long amount) {
        Long currentBalance = wallet.getBalance();
        wallet.setBalance(currentBalance + amount);
        walletRepository.save(wallet);
    }

    public void decrementMoney(Wallet wallet, Long amount) {
        Long currentBalance = wallet.getBalance();
        wallet.setBalance(currentBalance - amount);
        walletRepository.save(wallet);
    }

    public Optional<Wallet> getWalletByCredential(String credential) {
        try {
            UUID walletId = UUID.fromString(credential);
            return walletRepository.findById(walletId);
        } catch (IllegalArgumentException e) {
            return walletRepository.findByUserPhone(credential);
        }
    }

    public Optional<Wallet> getWalletByUserId(UUID userId) {
        return walletRepository.findByUserId(userId);
    }
}
