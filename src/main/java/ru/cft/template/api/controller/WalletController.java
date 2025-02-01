package ru.cft.template.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.model.wallet.WalletModel;
import ru.cft.template.core.service.WalletService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{walletId}")
    public WalletModel getWallet(@PathVariable UUID walletId) {
        return walletService.getWallet(walletId);
    }

    @PostMapping("/{walletId}/hesoyam")
    public ResponseEntity<String> createWallet(@PathVariable UUID walletId) {
        return ResponseEntity.ok(walletService.hesoyam(walletId));
    }
}
