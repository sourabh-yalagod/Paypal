package com.paypal.user_service.lib;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new RuntimeException("Resource not found...!");
        }
        return new RuntimeException("Feign error: " + response.status());
    }
}

