package ru.cft.template.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.cft.template.api.model.wallet.WalletModel;
import ru.cft.template.core.entity.Wallet;
import ru.cft.template.core.exception.DoesntHaveRightsException;
import ru.cft.template.core.exception.ExceptionMessage;
import ru.cft.template.core.exception.WalletNotFoundException;
import ru.cft.template.core.mapper.WalletMapper;
import ru.cft.template.core.repository.WalletRepository;
import ru.cft.template.core.security.userDetails.CustomUserDetails;

import java.security.SecureRandom;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final SecureRandom secureRandom;

    public WalletModel getWallet(UUID walletId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Wallet wallet = walletRepository.findById(walletId).orElseThrow(WalletNotFoundException::new);

        if (!wallet.getUser().getId().equals(userDetails.getId())) {
            throw new DoesntHaveRightsException(ExceptionMessage.GETTING_OTHER_WALLET_NOT_ALLOWED);
        }

        return walletMapper.toWalletDto(wallet);
    }

    public String hesoyam(UUID walletId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(WalletNotFoundException::new);

        if (!wallet.getUser().getId().equals(userDetails.getId())) {
            throw new DoesntHaveRightsException(ExceptionMessage.GETTING_OTHER_WALLET_NOT_ALLOWED);
        }

        double chance = secureRandom.nextDouble();
        if (chance < 0.25) {
            Long currentBalance = wallet.getBalance();
            wallet.setBalance(currentBalance + 100);
            walletRepository.save(wallet);
            return "Поздравляем! Баланс увеличен на 100. Текущий баланс: " + wallet.getBalance();
        } else {
            return "Повезет в следующий раз! Баланс остался без изменений: " + wallet.getBalance();
        }
    }
}
