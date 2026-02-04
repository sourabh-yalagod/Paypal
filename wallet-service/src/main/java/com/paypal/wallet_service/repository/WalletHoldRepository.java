package com.paypal.wallet_service.repository;

import com.paypal.wallet_service.entity.WalletEntity;
import com.paypal.wallet_service.entity.WalletHoldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletHoldRepository extends JpaRepository<WalletHoldEntity, String> {
}
