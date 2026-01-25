package com.paypal.user_service.service;

import com.paypal.user_service.dto.auth.SignInDto;
import com.paypal.user_service.dto.auth.SignUpDto;
import com.paypal.user_service.lib.CustomResponse;

public interface UserAuth {
    public CustomResponse signUp(SignUpDto payload);
    public CustomResponse signIn(SignInDto payload);
}
