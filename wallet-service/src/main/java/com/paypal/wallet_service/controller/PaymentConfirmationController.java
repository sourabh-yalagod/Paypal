package com.paypal.wallet_service.controller;

import com.paypal.wallet_service.dto.PaymentResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-status")
public class PaymentConfirmationController {
    public ResponseEntity<?> updatePaymentStatus(@RequestBody PaymentResponseDto payload){
        return null;
    }
}
