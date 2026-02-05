package com.paypal.wallet_service.service;

import com.paypal.wallet_service.dto.CreateWalletRequestDto;
import com.paypal.wallet_service.dto.CreditRequestDto;
import com.paypal.wallet_service.dto.DebitRequestDto;
import com.paypal.wallet_service.entity.WalletEntity;
import com.paypal.wallet_service.entity.WalletHoldEntity;
import com.paypal.wallet_service.kafka.KafkaProducer;
import com.paypal.wallet_service.lib.CustomResponse;
import com.paypal.wallet_service.lib.HoldStatus;
import com.paypal.wallet_service.repository.WalletHoldRepository;
import com.paypal.wallet_service.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletHoldRepository walletHoldRepository;
    private final KafkaProducer kafkaProducer;

    @Transactional
    @Override
    public CustomResponse createWallet(CreateWalletRequestDto payload) {
        try {
            Optional<WalletEntity> isWalletExist = this.walletRepository.findWalletByUserId(payload.getUserId());
            if (isWalletExist.isPresent())
                throw new RuntimeException("Wallet already Exist of user with Id : " + payload.getUserId());
            WalletEntity walletPayload = WalletEntity
                    .builder()
                    .balance(payload.getBalance())
                    .userId(payload.getUserId())
                    .walletHolds(new ArrayList<>())
                    .build();
            WalletEntity record = this.walletRepository.save(walletPayload);
            return CustomResponse
                    .builder()
                    .status(HttpStatus.CREATED.value())
                    .isSuccess(true)
                    .message("Wallet created successfully.")
                    .data(record)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public CustomResponse credit(CreditRequestDto payload) {

        WalletEntity wallet = walletRepository
                .findWalletByUserId(payload.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.credit(payload.getAmount());

        return CustomResponse.builder()
                .isSuccess(true)
                .status(200)
                .message("Wallet credited successfully")
                .data(wallet)
                .build();
    }

    @Transactional
    @Override
    public CustomResponse placeHold(DebitRequestDto payload) {

        WalletEntity wallet = walletRepository
                .findWalletByUserId(payload.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (!wallet.hasSufficientBalance(payload.getAmount()))
            throw new RuntimeException("Insufficient balance");

        wallet.reduceAvailable(payload.getAmount());

        WalletHoldEntity hold = WalletHoldEntity.builder()
                .wallet(wallet)
                .amount(payload.getAmount())
                .status(HoldStatus.ACTIVE)
                .holdReference("HOLD-" + UUID.randomUUID())
                .build();

        walletHoldRepository.save(hold);

        /* Publish Kafka AFTER COMMIT */
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        kafkaProducer.publishEvent(
                                "hold-request-verification-events",
                                hold
                        );
                    }
                });

        return CustomResponse.builder()
                .isSuccess(true)
                .status(200)
                .message("Amount placed on hold")
                .data(hold)
                .build();
    }

    @Transactional
    public CustomResponse captureHold(String holdReference) {

        WalletHoldEntity hold = walletHoldRepository
                .findByHoldReference(holdReference)
                .orElseThrow(() -> new RuntimeException("Hold not found"));

        if (hold.getStatus() != HoldStatus.ACTIVE)
            throw new IllegalStateException("Hold not active");

        WalletEntity wallet = hold.getWallet();

        wallet.debit(hold.getAmount());
        hold.capture();

        return CustomResponse.builder()
                .isSuccess(true)
                .status(200)
                .message("Transaction successful")
                .data(wallet)
                .build();
    }

    @Transactional
    public CustomResponse releaseHold(String holdReference) {

        WalletHoldEntity hold = walletHoldRepository
                .findByHoldReference(holdReference)
                .orElseThrow(() -> new RuntimeException("Hold not found"));

        if (hold.getStatus() != HoldStatus.ACTIVE)
            throw new IllegalStateException("Hold not active");

        WalletEntity wallet = hold.getWallet();

        // IMPORTANT FIX
        wallet.increaseAvailable(hold.getAmount());

        hold.release();

        return CustomResponse.builder()
                .isSuccess(true)
                .status(200)
                .message("Hold released")
                .data(wallet)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public CustomResponse findWalletBalance(String userId) {

        WalletEntity wallet = walletRepository
                .findWalletByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return CustomResponse.builder()
                .isSuccess(true)
                .status(200)
                .message("Wallet balance fetched")
                .data(wallet.getAvailableBalance())
                .build();
    }
}
