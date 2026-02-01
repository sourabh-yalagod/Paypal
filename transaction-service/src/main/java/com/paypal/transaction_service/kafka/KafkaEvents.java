package com.paypal.transaction_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class KafkaEvents {
    private static final String TOPIC = "transaction-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public boolean publishEvent(String key, String event) {
        CompletableFuture<SendResult<String, String>> futureEvent = kafkaTemplate.send(TOPIC, key, event);
        futureEvent.thenAccept(response -> {
            RecordMetadata metadata = response.getRecordMetadata();
            ProducerRecord<String, String> record = response.getProducerRecord();
            System.out.println("Event Published Response : " + metadata.topic());
            System.out.println("Event Published Record : " + record.toString());
        }).exceptionally(error -> {
            System.out.println("Event Published Error : " + error.getMessage());
            return null;
        });
        return true;
    }

}
