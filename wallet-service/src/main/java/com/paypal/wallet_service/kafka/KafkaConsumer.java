package com.paypal.wallet_service.kafka;

import com.paypal.wallet_service.dto.PaymentResponseDto;
import com.paypal.wallet_service.entity.TransactionEntity;
import com.paypal.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final WalletService walletService;

    @KafkaListener(
            topics = "TransactionEvents",
            containerFactory = "transactionKafkaListenerContainerFactory"
    )
    public void consumeTransactionTopic(TransactionEntity transaction) {
        System.out.println("TransactionEvents : " + transaction);
        walletService.placeHold(transaction);
    }

    @KafkaListener(
            topics = "WalletHoldEvents",
            containerFactory = "walletKafkaListenerContainerFactory"
    )

    @Transactional
    public void consumeWalletTopic(PaymentResponseDto payload) {
        System.out.println("WalletHoldEvents : " + payload);
        try {
            walletService.captureHold(payload);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            walletService.releaseHold(payload);
        }
    }
}
