package ru.cft.template.api.model.transfer;

import ru.cft.template.core.enumeration.TransferStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TransferDto(
        UUID transferId,
        Long amount,
        TransferStatus transferStatus,
        OffsetDateTime createdAt
) {
}
