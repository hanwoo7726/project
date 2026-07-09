package com.sparta.project.jwt;

import com.sparta.project.entity.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEATDER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";

    private final long TOKEN_TIME = 60 * 60 * 1000L; // 1시간

    private final SecretKey secretKey;


    public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );
    }


    // JWT 생성
    public String createToken(String username, UserRoleEnum role){
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .subject(username)
                .claim(AUTHORIZATION_KEY, role)
                .issuedAt(date)
                .expiration(new Date(date.getTime() + TOKEN_TIME))
                .signWith(secretKey)
                .compact();

    }

    public String getTokenFromRequest(HttpServletRequest request){
        return request.getHeader(AUTHORIZATION_HEATDER);
    }



    public String substringToken(String tokenValue) {
        if (tokenValue != null && tokenValue.startsWith(BEARER_PREFIX)){
            return tokenValue.substring(BEARER_PREFIX.length());
        }

        throw new IllegalArgumentException("토큰 형식이 올바르지 않습니다.");

    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

























}
