package com.sparta.project.auth.controller;

import com.sparta.project.auth.dto.LoginRequestDto;
import com.sparta.project.auth.dto.ReissueRequestDto;
import com.sparta.project.auth.dto.TokenResponseDto;
import com.sparta.project.auth.jwt.JwtUtil;
import com.sparta.project.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        TokenResponseDto tokenResponseDto = authService.loginUser(dto);

        return ResponseEntity.ok().header(JwtUtil.AUTHORIZATION_HEADER,
                tokenResponseDto.getAccessToken())
                .body(tokenResponseDto);

    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(@Valid @RequestBody ReissueRequestDto dto) {
        TokenResponseDto tokenResponse = authService.reissue(dto);

        return ResponseEntity.ok().header(JwtUtil.AUTHORIZATION_HEADER,
                tokenResponse.getAccessToken())
                .body(tokenResponse);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        authService.logout(authentication.getName());

        return ResponseEntity.noContent().build();
    }


}
