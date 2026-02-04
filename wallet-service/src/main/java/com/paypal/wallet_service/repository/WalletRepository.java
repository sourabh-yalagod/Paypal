package com.paypal.wallet_service.repository;

import com.paypal.wallet_service.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, String> {
    public Optional<WalletEntity> findWalletByUserId(String userId);
}
