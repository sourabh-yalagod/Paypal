package com.paypal.user_service.service;

import com.paypal.user_service.entity.TransactionEntity;
import com.paypal.user_service.entity.UserEntity;
import com.paypal.user_service.entity.WalletEntity;
import com.paypal.user_service.entity.WalletHoldEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<UserEntity> getUserById(String userId);
    public Optional<UserEntity> getUserByEmail(String email);
    public Optional<UserEntity> getUserByUsername(String username);
    public List<UserEntity> getAllUser();
    public List<TransactionEntity> getUserTransactions(String userId);
    public WalletEntity getUserWallet(String userId);
    public List<WalletHoldEntity> getUserWalletHolds(String userId);
}
