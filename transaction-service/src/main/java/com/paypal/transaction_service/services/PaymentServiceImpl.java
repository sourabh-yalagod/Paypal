package com.paypal.transaction_service.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.transaction_service.dtos.PaymentRequestDto;
import com.paypal.transaction_service.entity.TransactionEntity;
import com.paypal.transaction_service.entity.UserEntity;
import com.paypal.transaction_service.kafka.KafkaEvents;
import com.paypal.transaction_service.lib.CustomResponse;
import com.paypal.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final TransactionRepository transactionRepository;
    private final KafkaEvents kafkaEvents;
    private final ObjectMapper objectMapper;

    @Override
    public CustomResponse pay(PaymentRequestDto payload) {
        Optional<TransactionEntity> sender = transactionRepository.findUserById(payload.getSenderId());
        TransactionEntity transactionPayload = TransactionEntity.builder()
                .amount(payload.getAmount())
                .receiverId(payload.getReceiverId())
                .senderId(payload.getSenderId())
                .build();
        TransactionEntity newTransaction = transactionRepository.save(transactionPayload);
        kafkaEvents.publishEvent(newTransaction.getId(), newTransaction);
        return CustomResponse.builder()
                .message("Transaction created")
                .isSuccess(true)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }
}
