package com.paypal.transaction_service.services;


import com.paypal.transaction_service.dtos.PaymentRequestDto;
import com.paypal.transaction_service.entity.TransactionEntity;
import com.paypal.transaction_service.kafka.KafkaEventProducer;
import com.paypal.transaction_service.lib.CustomResponse;
import com.paypal.transaction_service.lib.KafkaTopics;
import com.paypal.transaction_service.lib.TransactionStatus;
import com.paypal.transaction_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final TransactionRepository transactionRepository;
    private final KafkaEventProducer kafkaEvents;

    @Override
    public CustomResponse pay(PaymentRequestDto payload) {
        try {
            TransactionEntity transactionPayload = TransactionEntity.builder()
                    .amount(payload.getAmount())
                    .receiverId(payload.getReceiverId())
                    .senderId(payload.getSenderId())
                    .status(TransactionStatus.PENDING)
                    .build();
            TransactionEntity newTransaction = transactionRepository.save(transactionPayload);
            kafkaEvents.publishEvent(newTransaction.getId(), newTransaction, KafkaTopics.TransactionEvents);
            return CustomResponse.builder()
                    .message("Transaction created.")
                    .isSuccess(true)
                    .status(HttpStatus.CREATED.value())
                    .build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
