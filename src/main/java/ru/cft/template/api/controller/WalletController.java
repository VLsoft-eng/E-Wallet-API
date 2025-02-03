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

    private final WalletService walletService;

    @GetMapping("/{walletId}")
    public WalletDto getWallet(@PathVariable UUID walletId) {
        return walletService.getWallet(walletId);
    }

    @PostMapping("/{walletId}/hesoyam")
    public WalletDto createWallet(@PathVariable UUID walletId) {
        return walletService.hesoyam(walletId);
    }
}
