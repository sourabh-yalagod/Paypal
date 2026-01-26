package com.paypal.user_service.lib;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.user_service.lib.CustomResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void writeError(
            HttpServletResponse response,
            int status,
            String message
    ) throws IOException {

        CustomResponse customResponse = CustomResponse.builder()
                .status(status)
                .message(message)
                .isSuccess(false)
                .data(null)
                .build();

        response.setStatus(status);
        response.setContentType("application/json");

        response.getWriter().write(
                mapper.writeValueAsString(customResponse)
        );
    }
}
