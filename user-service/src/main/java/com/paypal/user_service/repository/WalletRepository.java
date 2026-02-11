package com.paypal.user_service.repository;

import com.paypal.user_service.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, String> {
    Optional<WalletEntity> findByUserId(String userId);
}
