package com.paypal.user_service.repository;

import com.paypal.user_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    public Optional<UserEntity> getUserByEmail(String email);
    public Optional<UserEntity> getUserByUsername(String username);
}