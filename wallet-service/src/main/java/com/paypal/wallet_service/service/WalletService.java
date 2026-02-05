package com.paypal.wallet_service.service;

import com.paypal.wallet_service.dto.CreateWalletRequestDto;
import com.paypal.wallet_service.dto.CreditRequestDto;
import com.paypal.wallet_service.dto.DebitRequestDto;
import com.paypal.wallet_service.entity.WalletEntity;
import com.paypal.wallet_service.lib.CustomResponse;

public interface WalletService {
    public CustomResponse createWallet(CreateWalletRequestDto payload);
    public CustomResponse credit(CreditRequestDto payload);
    public CustomResponse placeHold(DebitRequestDto payload);
    public CustomResponse findWalletBalance(String userId);
}
