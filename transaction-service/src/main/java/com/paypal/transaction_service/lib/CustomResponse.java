package com.paypal.transaction_service.lib;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse {
    private String message;
    private Object data;
    private int statusCode;
    private boolean isSuccess;
}
