package com.sparta.project.auth.jwt;

import com.sparta.project.user.entity.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_KEY = "auth";
    public static final String TOKEN_TYPE_KEY = "type";
    public static final String ACCESS_TOKEN_TYPE = "ACCESS";
    public static final String REFRESH_TOKEN_TYPE = "REFRESH";
    public static final String BEARER_PREFIX = "Bearer ";

    private final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 1시간

    private final long REFRESH_TOKEN_TIME
            = 7 * 24 * 60 * 60 * 1000L;


    private final SecretKey secretKey;

    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );
    }

    // 토큰 생성




    // JWT 생성
    public String createToken(String username, UserRoleEnum role){
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .subject(username)
                .claim(AUTHORIZATION_KEY, role.getAuthority())
                .claim(TOKEN_TYPE_KEY,ACCESS_TOKEN_TYPE)
                .issuedAt(date)
                .expiration(new Date(date.getTime() +ACCESS_TOKEN_TIME))
                .signWith(secretKey)
                .compact();

    }

    // 토큰 재발급
    public String createRefreshToken(String username) {
        Date date = new Date();
        return Jwts.builder()
                .subject(username)
                .claim(TOKEN_TYPE_KEY, REFRESH_TOKEN_TYPE)
                .issuedAt(date)
                .expiration(
                        new Date(date.getTime() + REFRESH_TOKEN_TIME)
                )
                .compact();
    }

    // 요청 헤더에서 Access Token 가져오기
    public String getTokenFromRequest(HttpServletRequest request){
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    // Bearer 제거
    public String substringToken(String tokenValue) {
        if (tokenValue != null && tokenValue.startsWith(BEARER_PREFIX)){
            return tokenValue.substring(BEARER_PREFIX.length());
        }

        throw new IllegalArgumentException("토큰 형식이 올바르지 않습니다.");

    }

    // Access Token 인지 확인
    public boolean isAccessToken(String token) {
        Claims claims =
                getUserInfoFromToken(token);

        return ACCESS_TOKEN_TYPE.equals(
                claims.get(
                        TOKEN_TYPE_KEY,
                        String.class
                )
        );
    }

    // Refresh Token 인지 확인
    public boolean isRefreshToken(String token) {
        Claims claims =
                getUserInfoFromToken(token);

        return REFRESH_TOKEN_TYPE.equals(
                claims.get(
                        TOKEN_TYPE_KEY,
                        String.class
                )
        );
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

    // 토큰 Claims 조회
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // username 조회
    public String getUsernameFromToken(String token) {
        return getUserInfoFromToken(token).getSubject();

    }

    // 만료시간 조회
    public Date getExpiration(String token) {
        return getUserInfoFromToken(token).getExpiration();
    }



}
