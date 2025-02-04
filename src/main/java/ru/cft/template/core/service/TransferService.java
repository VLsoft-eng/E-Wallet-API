package ru.cft.template.core.service;

import ru.cft.template.api.model.transfer.CreateTransferRequest;
import ru.cft.template.api.model.transfer.GetTransferListRequestParams;
import ru.cft.template.api.model.transfer.TransferDto;
import ru.cft.template.api.model.transfer.TransferListDto;

import java.util.UUID;

public interface TransferService {
    TransferDto createTransfer(CreateTransferRequest request);
    TransferDto getTransfer(UUID transferId);
    TransferListDto getTransferList(GetTransferListRequestParams params);
}
