package com.paypal.wallet_service.controller;

import com.paypal.wallet_service.dto.PaymentResponseDto;
import com.paypal.wallet_service.lib.CustomResponse;
import com.paypal.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-status")
@RequiredArgsConstructor
public class PaymentConfirmationController {
    private final WalletService walletService;

    @PostMapping("/confirm")
    public ResponseEntity<?> updatePaymentStatus(@RequestBody PaymentResponseDto payload) {
        try {
            if (payload.getIsSuccess()) {
                CustomResponse response = walletService.captureHold(payload);
                return ResponseEntity.ok(response);
            } else {
                CustomResponse response = walletService.releaseHold(payload);
                return ResponseEntity.ok(response);
            }
        } catch (RuntimeException e) {
            System.out.println(e.toString());
            throw new RuntimeException(e.getMessage());
        }
    }
}
