package ru.cft.template.api.model.transfer;

import java.util.List;

public record TransferListDto(
        List<TransferDto> transfers
) {
}
