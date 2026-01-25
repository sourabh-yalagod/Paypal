package com.paypal.user_service.service;

import com.paypal.user_service.dto.auth.SignInDto;
import com.paypal.user_service.dto.auth.SignUpDto;
import com.paypal.user_service.entity.UserEntity;
import com.paypal.user_service.lib.CustomResponse;
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

        // check email
        if (userRepository.getUserByEmail(payload.getEmail()).isPresent()) {
            return CustomResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Email already exists")
                    .isSuccess(false)
                    .build();
        }

        // check username
        if (userRepository.getUserByUsername(payload.getUsername()).isPresent()) {
            return CustomResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Username already exists")
                    .isSuccess(false)
                    .build();
        }

        payload.setPassword(passwordEncoder.encode(payload.getPassword()));

        UserEntity user = UserEntity.builder()
                .email(payload.getEmail())
                .username(payload.getUsername())
                .password(payload.getPassword())
                .build();

        user = userRepository.save(user);

        String token = JwtUtils.generateToken(user);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);

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
            return CustomResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid email or password")
                    .isSuccess(false)
                    .build();
        }

        UserEntity user = userOpt.get();

        if (!passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            return CustomResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid email or password")
                    .isSuccess(false)
                    .build();
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
