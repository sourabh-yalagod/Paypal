package com.paypal.wallet_service.lib;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomResponse {
    private String message;
    private Integer status;
    private Object data;
    private Boolean isSuccess = Boolean.FALSE;
}
