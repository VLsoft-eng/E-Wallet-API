package ru.cft.template.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.model.wallet.WalletDto;
import ru.cft.template.core.service.WalletService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService service;

    @GetMapping("/{walletId}")
    public WalletDto getWallet(@PathVariable UUID walletId) {
        return service.getWallet(walletId);
    }

    @PostMapping("/{walletId}/hesoyam")
    public WalletDto createWallet(@PathVariable UUID walletId) {
        return service.hesoyam(walletId);
    }
}
