package com.paypal.user_service.controller;

import com.paypal.user_service.dto.apis.CreateWalletRequestDto;
import com.paypal.user_service.dto.auth.SignInDto;
import com.paypal.user_service.dto.auth.SignUpDto;
import com.paypal.user_service.externalServices.WalletService;
import com.paypal.user_service.lib.CustomResponse;
import com.paypal.user_service.service.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserAuthController {
    private final UserAuth userAuth;
    private final WalletService walletService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto payload) {
        try {
            CustomResponse response = userAuth.signUp(payload);
            walletService.createWalletForUser(CreateWalletRequestDto.builder().userId("1").balance(0.0).currency("INR").build());
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInDto payload) {
        CustomResponse response = userAuth.signIn(payload);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
