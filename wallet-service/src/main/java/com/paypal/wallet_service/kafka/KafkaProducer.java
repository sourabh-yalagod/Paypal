package com.paypal.wallet_service.kafka;

import com.paypal.wallet_service.entity.TransactionEntity;
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
    private static final String TOPIC = "wallet-events";
    private final KafkaTemplate<String, TransactionEntity> kafkaTemplate;

    public void publishEvent(String key, TransactionEntity event) {
        CompletableFuture<SendResult<String, TransactionEntity>> futureEvent = kafkaTemplate.send(TOPIC, key, event);
        futureEvent.thenAccept(response -> {
            RecordMetadata metadata = response.getRecordMetadata();
            ProducerRecord<String, TransactionEntity> record = response.getProducerRecord();
            System.out.println("Wallet Event Published Succeessfully Topic: " + metadata.topic() + ", Partition: " + metadata.partition() + ", Offset: " + metadata.offset());
        }).exceptionally(error -> {
            System.out.println("Event Published Error : " + error.getMessage());
            return null;
        });
    }
}
