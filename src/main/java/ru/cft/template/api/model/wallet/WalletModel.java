package ru.cft.template.api.model.wallet;

import java.util.UUID;

public record WalletModel(
        UUID walletId,
        Long balance
) {
}
