package com.paypal.user_service.utils;

import com.paypal.user_service.entity.UserEntity;
import io.jsonwebtoken.*;

import java.util.Date;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtUtils {

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "this_is_a_very_secure_secret_key_256bit".getBytes()
    );;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 Day

    public static String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public static String getUserId(String token) {
        return getClaims(token).get("userId", String.class);
    }

    public static boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public static boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
