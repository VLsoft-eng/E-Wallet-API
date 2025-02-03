package ru.cft.template.core.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.api.model.transfer.TransferDto;
import ru.cft.template.api.model.transfer.TransferListDto;
import ru.cft.template.core.entity.Transfer;

import java.util.List;

@Component
public class TransferMapper {
    public TransferDto toTransferDto(Transfer transfer) {
        return new TransferDto(
                transfer.getId(),
                transfer.getAmount(),
                transfer.getTransferStatus(),
                transfer.getCreatedAt()
        );
    }

    public TransferListDto toTransferListDto(List<Transfer> transfers) {
        List<TransferDto> transferDtos = transfers.stream()
                .map(this::toTransferDto)
                .toList();

        return new TransferListDto(transferDtos);
    }
}
