package com.paypal.user_service.externalServices;

import com.paypal.user_service.dto.apis.CreateWalletRequestDto;
import com.paypal.user_service.dto.apis.CreditRequestDto;
import com.paypal.user_service.dto.apis.DebitRequestDto;
import com.paypal.user_service.lib.CustomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "wallet-service", url = "http://localhost:8085/api/wallets")
public interface WalletService {

    @PostMapping("/create-wallet")
    public CustomResponse createWalletForUser(CreateWalletRequestDto payload);

    @PostMapping("/credit-wallet")
    public CustomResponse creditWallet(CreditRequestDto payload);

    @PostMapping("/debit-wallet")
    public CustomResponse debitWallet(DebitRequestDto payload);
}
