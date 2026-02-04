package com.paypal.wallet_service.service;

import com.paypal.wallet_service.dto.CreateWalletRequestDto;
import com.paypal.wallet_service.dto.CreditRequestDto;
import com.paypal.wallet_service.dto.DebitRequestDto;
import com.paypal.wallet_service.entity.WalletEntity;
import com.paypal.wallet_service.entity.WalletHoldEntity;
import com.paypal.wallet_service.lib.CustomResponse;
import com.paypal.wallet_service.repository.WalletHoldRepository;
import com.paypal.wallet_service.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletHoldRepository walletHoldRepository;

    @Override
    public CustomResponse createWallet(CreateWalletRequestDto payload) {
        try {
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
            throw new RuntimeException("Wallet creation failed for user with Id : " + payload.getUserId());
        }
    }

    @Override
    public CustomResponse credit(CreditRequestDto payload) {
        try {
            WalletEntity wallet = walletRepository.findWalletByUserId(payload.getUserId()).orElseThrow(() -> new RuntimeException("wallet not found for user with id : " + payload.getUserId()));
            wallet.setBalance(wallet.getBalance() + payload.getAmount());
            walletRepository.save(wallet);
            return CustomResponse.builder()
                    .isSuccess(true)
                    .status(200)
                    .message("Wallet credited successfully")
                    .data(wallet)
                    .build();
        } catch (RuntimeException e) {
            throw new RuntimeException("Wallet credit failed....!");
        }
    }

    @Override
    public CustomResponse holdAmountForCredit(DebitRequestDto payload) {
        WalletEntity wallet = walletRepository.findWalletByUserId(payload.getUserId()).orElseThrow(() -> new RuntimeException("wallet not found for user with id : " + payload.getUserId()));
        if (wallet.getBalance() < payload.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }
        WalletHoldEntity hold = WalletHoldEntity.builder()
                .amount(payload.getAmount())
                .build();
        hold.setWallet(wallet);
        wallet.getWalletHolds().add(hold);
        walletHoldRepository.save(hold);
        return CustomResponse.builder()
                .isSuccess(true)
                .status(200)
                .message("Amount successfully placed on hold")
                .data(hold)
                .build();
    }

    public CustomResponse findUserBalance(String userId) {
        WalletEntity wallet = walletRepository.findWalletByUserId(userId).orElseThrow(() -> new RuntimeException("wallet not found for user with id : " + userId));
        AtomicReference<Double> balance = new AtomicReference<>(wallet.getBalance());
        wallet.getWalletHolds().forEach(holds -> {
            balance.updateAndGet(v -> v - holds.getAmount());
        });
        return CustomResponse.builder()
                .isSuccess(true)
                .status(200)
                .message("user wallet worth : " + balance.get().toString())
                .data(wallet)
                .build();
    }
}
