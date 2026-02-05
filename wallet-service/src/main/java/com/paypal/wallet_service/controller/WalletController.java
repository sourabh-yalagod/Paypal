package com.paypal.wallet_service.controller;

import com.paypal.wallet_service.dto.CreateWalletRequestDto;
import com.paypal.wallet_service.dto.CreditRequestDto;
import com.paypal.wallet_service.dto.DebitRequestDto;
import com.paypal.wallet_service.lib.CustomResponse;
import com.paypal.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/create-wallet")
    public ResponseEntity<?> creditWallet(@RequestBody CreateWalletRequestDto payload) {
        CustomResponse response = walletService.createWallet(payload);
        if (response.getIsSuccess()) {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
        throw new RuntimeException(response.getMessage().isBlank() ? "wallet creation failed...!" : response.getMessage());
    }

    @PostMapping("/credit-wallet")
    public ResponseEntity<?> creditWallet(@RequestBody CreditRequestDto payload) {
        try {
            CustomResponse response = walletService.credit(payload);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/debit-wallet")
    public ResponseEntity<?> debitWallet(@RequestBody DebitRequestDto payload) {
        CustomResponse response = walletService.placeHold(payload);
        if (response.getIsSuccess()) {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
        throw new RuntimeException("Debit wallet failed...!");
    }

    @GetMapping("/check-wallet-balance/{userId}")
    public ResponseEntity<?> checkWalletBalance(@PathVariable String userId) {
        CustomResponse response = walletService.findWalletBalance(userId);
        if (response.getIsSuccess()) {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
        throw new RuntimeException("Debit wallet failed...!");
    }
}
