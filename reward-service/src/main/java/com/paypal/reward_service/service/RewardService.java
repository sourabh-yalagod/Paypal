package com.paypal.reward_service.service;

import com.paypal.reward_service.entity.RewardEntity;
import com.paypal.reward_service.entity.TransactionEntity;
import com.paypal.reward_service.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardRepository rewardRepository;

    @KafkaListener(topics = "transaction-events", groupId = "reward-group")
    public void listenKafkaTransaction(TransactionEntity event) {
        try {
            RewardEntity payload = RewardEntity.builder()
                    .transactionId(event.getId())
                    .userId(event.getSenderId())
                    .points(event.getAmount().doubleValue() * 100)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            RewardEntity newRecord = rewardRepository.save(payload);
            System.out.println("New Reward : " + newRecord);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
