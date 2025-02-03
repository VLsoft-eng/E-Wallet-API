package ru.cft.template.api.model.wallet;

import java.util.UUID;

public record WalletDto(
        UUID walletId,
        Long balance
) {
}
