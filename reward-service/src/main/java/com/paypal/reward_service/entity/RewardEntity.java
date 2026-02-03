package com.paypal.reward_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "rewards")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double points;
    private String userId;
    private String transactionId;
    @CreatedDate
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
