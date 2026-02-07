package com.paypal.wallet_service.repository;

import com.paypal.wallet_service.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
}
