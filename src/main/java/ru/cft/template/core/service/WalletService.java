package ru.cft.template.core.service;

import ru.cft.template.api.model.wallet.WalletDto;
import ru.cft.template.core.entity.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletService {
    WalletDto getWallet(UUID walletId);

    WalletDto hesoyam(UUID walletId);

    void incrementMoney(Wallet wallet, Long amount);

    void decrementMoney(Wallet wallet, Long amount);

    Optional<Wallet> getWalletByCredential(String credential);

    Optional<Wallet> getWalletByUserId(UUID userId);
}
