package com.paypal.user_service.controller;

import com.paypal.user_service.dto.auth.SignInDto;
import com.paypal.user_service.dto.auth.SignUpDto;
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

    @PostMapping("/api/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto payload) {
        CustomResponse response = userAuth.signUp(payload);
        return ResponseEntity.status(response.getStatus()).body(response.getMessage());
    }

    @PostMapping("/api/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInDto payload) {
        CustomResponse response = userAuth.signIn(payload);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
