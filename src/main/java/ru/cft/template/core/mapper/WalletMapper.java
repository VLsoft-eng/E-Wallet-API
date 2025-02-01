package ru.cft.template.core.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.api.model.wallet.WalletModel;
import ru.cft.template.core.entity.Wallet;

@Component
public class WalletMapper {
    public WalletModel toWalletDto(Wallet wallet) {
        return new WalletModel(
                wallet.getId(),
                wallet.getBalance()
        );
    }
}

