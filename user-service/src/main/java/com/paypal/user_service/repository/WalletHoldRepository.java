package com.paypal.user_service.repository;

import com.paypal.user_service.entity.WalletEntity;
import com.paypal.user_service.entity.WalletHoldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletHoldRepository extends JpaRepository<WalletHoldEntity, String> {
    List<WalletHoldEntity> findByWalletId(String walletId);
}
