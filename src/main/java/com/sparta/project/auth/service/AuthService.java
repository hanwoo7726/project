package com.sparta.project.auth.service;

import com.sparta.project.auth.dto.LoginRequestDto;
import com.sparta.project.auth.dto.ReissueRequestDto;
import com.sparta.project.auth.dto.TokenResponseDto;
import com.sparta.project.auth.entity.RefreshToken;
import com.sparta.project.auth.jwt.JwtUtil;
import com.sparta.project.auth.repository.RefreshTokenRepository;
import com.sparta.project.user.entity.User;
import com.sparta.project.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponseDto loginUser(LoginRequestDto dto) {
        User user = userRepository.findByUsernameAndDeletedAtIsNull(dto.getUsername()).orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");

        }

        String accessToken = jwtUtil.createToken(user.getUsername(), user.getUserRoleEnum());

        String refreshToken = jwtUtil.createRefreshToken(user.getUsername());

        LocalDateTime expiresAt = jwtUtil.getExpiration(refreshToken);

        // 기존 Refresh Token이 있으면 교체
        RefreshToken savedRefreshToken = refreshTokenRepository.findByUsername(user.getUsername()).map(existingToken -> {
            existingToken.update(refreshToken,expiresAt);
            return existingToken;
        }).orElseGet(() -> new RefreshToken(user.getUsername(), refreshToken,expiresAt));

        refreshTokenRepository.save(savedRefreshToken);

        return new TokenResponseDto(accessToken, refreshToken);

    }

    // 토큰 재발급
    @Transactional
    public TokenResponseDto reissue(ReissueRequestDto dto) {
        String refreshToken = dto.getRefreshToken();

        // 서명, 만료시간 검증
        if (!jwtUtil.validateToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 Refresh Token입니다.");
        }

        // DB에 저장된 Refresh Token인지 확인
        RefreshToken savedRefreshToken = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new RuntimeException("저장되지 않은 Refresh Token입니다."));

        String username = jwtUtil.getUsernameFromToken(refreshToken);

        // JWT 사용자와 DB 사용자가 같은지 확인
        if (!savedRefreshToken.getUsername().equals(username)) {
            throw new RuntimeException("Refresh Token 사용자 정보가 다릅니다.");
        }

        // 탈퇴하지 않은 회원인지 확인
        User user = userRepository.findByUsernameAndDeletedAtIsNull(username).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        // 새 토큰 발급
        String newAccessToken = jwtUtil.createToken(user.getUsername(), user.getUserRoleEnum());

        String newRefreshToken = jwtUtil.createRefreshToken(user.getUsername());

        LocalDateTime expiresAt = jwtUtil.getExpiration(newRefreshToken);

        // 기존 Refresh Token 교체
        savedRefreshToken.update(newRefreshToken, expiresAt);

        return new TokenResponseDto(newAccessToken, newRefreshToken);
    }

    // 로그아웃
    @Transactional
    public void logout(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }


}
