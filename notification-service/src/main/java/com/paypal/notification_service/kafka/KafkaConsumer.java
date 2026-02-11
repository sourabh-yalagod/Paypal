package com.paypal.notification_service.kafka;

import com.paypal.notification_service.entity.TransactionEntity;
import com.paypal.notification_service.lib.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    @KafkaListener(
            topics = "TransactionStatus",
            containerFactory = "transactionKafkaListenerContainerFactory"
    )
    public void consumeTransactionTopic(TransactionEntity transaction) {
        System.out.println("TransactionEvents : " + transaction);
    }
}
