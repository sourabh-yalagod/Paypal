package com.paypal.transaction_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private String senderId;
    private String receiverId;
    private BigDecimal amount;
}
