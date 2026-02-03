package com.paypal.user_service.config;


import com.paypal.user_service.lib.AccessDeniedHandler;
import com.paypal.user_service.lib.JwtAuthEntryPoint;
import com.paypal.user_service.lib.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AccessDeniedHandler accessDeniedHandler;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http -> http
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/test").permitAll()
                        .requestMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.toString())
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
