package com.paypal.transaction_service.repository;

import com.paypal.transaction_service.entity.TransactionEntity;
import com.paypal.transaction_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    Optional<TransactionEntity> findUserById(String userId);
}
