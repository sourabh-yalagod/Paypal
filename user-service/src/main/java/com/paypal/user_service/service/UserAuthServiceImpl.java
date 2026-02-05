package com.paypal.user_service.service;

import com.paypal.user_service.dto.auth.SignInDto;
import com.paypal.user_service.dto.auth.SignUpDto;
import com.paypal.user_service.entity.UserEntity;
import com.paypal.user_service.lib.CustomResponse;
import com.paypal.user_service.lib.RoleEnum;
import com.paypal.user_service.repository.UserRepository;
import com.paypal.user_service.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuth {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomResponse signUp(SignUpDto payload) {
        String role = payload.getRole() == null ? RoleEnum.USER.toString() : payload.getRole();
        // check email
        if (userRepository.getUserByEmail(payload.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with This Email...!");
        }

        // check username
        if (userRepository.getUserByUsername(payload.getUsername()).isPresent()) {
            throw new RuntimeException("Username already Taken Please try with different Username...!");
        }

        payload.setPassword(passwordEncoder.encode(payload.getPassword()));

        UserEntity user = UserEntity.builder()
                .email(payload.getEmail())
                .username(payload.getUsername())
                .password(payload.getPassword())
                .role(RoleEnum.valueOf(role))
                .build();

        user = userRepository.save(user);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());

        return CustomResponse.builder()
                .status(HttpStatus.CREATED.value())
                .data(data)
                .message("User registered successfully")
                .isSuccess(true)
                .build();
    }

    @Override
    public CustomResponse signIn(SignInDto payload) {

        Optional<UserEntity> userOpt =
                userRepository.getUserByEmail(payload.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email not found....!");
        }

        UserEntity user = userOpt.get();

        if (!passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password....!");
        }

        String token = JwtUtils.generateToken(user);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);

        return CustomResponse.builder()
                .status(HttpStatus.OK.value())
                .data(data)
                .message("Login successful")
                .isSuccess(true)
                .build();
    }
}
