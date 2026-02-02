package com.paypal.notification_service.kafka;

import com.paypal.notification_service.entity.NotificationEntity;
import com.paypal.notification_service.entity.TransactionEntity;
import com.paypal.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "transaction-events", groupId = "notification-group")
    public void consumeTransaction(TransactionEntity transaction) {
        NotificationEntity payload = NotificationEntity
                .builder()
                .message(transaction.getAmount() + "Has been transferred from " + transaction.getSenderId() + " to " + transaction.getReceiverId())
                .senderId(transaction.getSenderId())
                .receiverId(transaction.getReceiverId())
                .build();
        NotificationEntity record = notificationRepository.save(payload);
        System.out.println("Notification Record : " + record);
    }
}
