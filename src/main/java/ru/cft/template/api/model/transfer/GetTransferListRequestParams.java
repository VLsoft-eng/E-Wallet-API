package ru.cft.template.api.model.transfer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cft.template.core.enumeration.TransferStatus;
import ru.cft.template.api.enumeration.TransferType;

import java.util.UUID;

public record GetTransferListRequestParams(
        @RequestParam(defaultValue = "1") @Min(1) Long page,
        @RequestParam(defaultValue = "10") @Min(1) @Max(100) Long size,
        @RequestParam(defaultValue = "ALL") TransferType type,
        @RequestParam(defaultValue = "PENDING") TransferStatus status,
        @RequestParam(required = false) UUID receiverId
) {}
