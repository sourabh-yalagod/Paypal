package com.paypal.user_service.controller;

import com.paypal.user_service.entity.UserEntity;
import com.paypal.user_service.entity.WalletEntity;
import com.paypal.user_service.entity.WalletHoldEntity;
import com.paypal.user_service.lib.CustomResponse;
import com.paypal.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Nice....!");
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable String userId) {
        UserEntity user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found...!"));
        CustomResponse response = CustomResponse.builder().status(HttpStatus.ACCEPTED.value()).message("User profile fetched.").data(user).build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/statistics/{userId}")
    public ResponseEntity<?> getUserStatistics(@PathVariable String userId) {
        WalletEntity wallet = userService.getUserWallet(userId);
        List<WalletHoldEntity> walletHolds = userService.getUserWalletHolds(wallet.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("wallet", wallet);
        map.put("walletHold", walletHolds);
        CustomResponse response = CustomResponse.builder().status(
                        HttpStatus.ACCEPTED.value())
                .message("user statistic fetched successfully.")
                .data(map).build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
