package com.paypal.transaction_service.controller;

import com.paypal.transaction_service.dtos.PaymentRequestDto;
import com.paypal.transaction_service.lib.CustomResponse;
import com.paypal.transaction_service.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<CustomResponse> createTransaction(@RequestBody PaymentRequestDto payload) {
        CustomResponse response = paymentService.pay(payload);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
