package com.paypal.user_service.service;

import com.paypal.user_service.entity.UserEntity;
import com.paypal.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
}
