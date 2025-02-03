package ru.cft.template.core.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.api.model.wallet.WalletDto;
import ru.cft.template.core.entity.Wallet;

@Component
public class WalletMapper {
    public WalletDto toWalletDto(Wallet wallet) {
        return new WalletDto(
                wallet.getId(),
                wallet.getBalance()
        );
    }
}

