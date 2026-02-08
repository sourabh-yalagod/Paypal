package com.paypal.wallet_service.kafka;

import com.paypal.wallet_service.dto.PaymentResponseDto;
import com.paypal.wallet_service.entity.TransactionEntity;
import com.paypal.wallet_service.entity.WalletHoldEntity;
import com.paypal.wallet_service.lib.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishEvent(String key, Object event, KafkaTopics topic) {
        CompletableFuture<SendResult<String, Object>> futureEvent = kafkaTemplate.send(topic.toString(), key, event);
        futureEvent.thenAccept(response -> {
            RecordMetadata metadata = response.getRecordMetadata();
            System.out.println("Kafka message sent successfully! Topic: " + metadata.topic() + ", Partition: " + metadata.partition() + ", Offset: " + metadata.offset());
        }).exceptionally(error -> {
            System.out.println("Event Published Error : " + error.getMessage());
            return null;
        });
    }
}