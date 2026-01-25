package com.paypal.user_service.lib;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomResponse {
    private int status;
    private Object data;
    private String message;
    private boolean isSuccess;
}
