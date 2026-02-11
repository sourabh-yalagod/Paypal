package com.paypal.user_service.service;

import com.paypal.user_service.entity.TransactionEntity;
import com.paypal.user_service.entity.UserEntity;
import com.paypal.user_service.entity.WalletEntity;
import com.paypal.user_service.entity.WalletHoldEntity;
import com.paypal.user_service.repository.TransactionRepository;
import com.paypal.user_service.repository.UserRepository;
import com.paypal.user_service.repository.WalletHoldRepository;
import com.paypal.user_service.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final WalletHoldRepository walletHoldRepository;

    @Override
    public Optional<UserEntity> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.getUserByEmail(username);
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public List<TransactionEntity> getUserTransactions(String userId) {
        return transactionRepository.findBySenderIdOrReceiverId(userId, userId);
    }

    @Override
    public WalletEntity getUserWallet(String userId) {
        return walletRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet not found for user with UserId : " + userId));
    }

    @Override
    public List<WalletHoldEntity> getUserWalletHolds(String walletId) {
        return walletHoldRepository.findByWalletId(walletId);
    }
}
