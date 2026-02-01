package com.paypal.transaction_service.services;

import com.paypal.transaction_service.dtos.PaymentRequestDto;
import com.paypal.transaction_service.lib.CustomResponse;

public interface PaymentService {
    CustomResponse pay(PaymentRequestDto payload);
}
