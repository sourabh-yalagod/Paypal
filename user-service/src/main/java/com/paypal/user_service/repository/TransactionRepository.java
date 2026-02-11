package com.paypal.user_service.repository;

import com.paypal.user_service.entity.TransactionEntity;
import com.paypal.user_service.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    List<TransactionEntity> findBySenderIdOrReceiverId(String senderId, String receiverId);
}
