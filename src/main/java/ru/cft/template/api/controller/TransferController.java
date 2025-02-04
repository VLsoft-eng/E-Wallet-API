package ru.cft.template.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.model.transfer.CreateTransferRequest;
import ru.cft.template.api.model.transfer.GetTransferListRequestParams;
import ru.cft.template.api.model.transfer.TransferDto;
import ru.cft.template.api.model.transfer.TransferListDto;
import ru.cft.template.core.service.TransferService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService service;

    @PostMapping("/create")
    public TransferDto createTransfer(@RequestBody @Valid CreateTransferRequest request) {
        return service.createTransfer(request);
    }

    @GetMapping("/{transferId}")
    public TransferDto getTransferById(@PathVariable UUID transferId) {
        return service.getTransfer(transferId);
    }

    @GetMapping
    public TransferListDto getTransfersList(GetTransferListRequestParams params) {
        return service.getTransferList(params);
    }
}
