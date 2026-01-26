package com.paypal.user_service.filters;

import com.paypal.user_service.lib.Constants;
import com.paypal.user_service.lib.ResponseUtils;
import com.paypal.user_service.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println(path);
        if (Constants.publicRouter.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            ResponseUtils.writeError(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Missing or invalid Authorization header"
            );
            return;
        }

        String token = header.substring(7);

        if (token.isBlank()) {
            ResponseUtils.writeError(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Auth Token is missing...!"
            );
            return;
        }

        if (!JwtUtils.isTokenExpired(token)) {
            ResponseUtils.writeError(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Auth Token is expired Please login...!"
            );
            return;
        }

        if (!JwtUtils.isTokenValid(token)) {
            ResponseUtils.writeError(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication failed Please Login once again.....!"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}
