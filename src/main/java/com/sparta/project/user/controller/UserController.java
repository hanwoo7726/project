package com.sparta.project.user.controller;

import com.sparta.project.auth.dto.LoginRequestDto;
import com.sparta.project.user.dto.PasswordRequestDto;
import com.sparta.project.user.dto.UserRequestDto;
import com.sparta.project.user.dto.UserResponseDto;
import com.sparta.project.auth.jwt.JwtUtil;
import com.sparta.project.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    // CRUD 기능 만들기
    // 유저 생성, 조회, 수정, 삭제

    private final UserService userService;

    // 전체 유저 조회
    @GetMapping()
    public List<UserResponseDto> findAllUsers() {
        return userService.findAllUsers();
    }

    // 회원 가입
    @PostMapping()
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    // 비밀번호 변경
    @PutMapping("/me/password")
    public String updatePassword(Authentication authentication,
                                 @Valid @RequestBody PasswordRequestDto dto) {
        userService.updatePassword(authentication.getName(), dto.getPassword());
        return "변경 완료";

    }

    // 유저 삭제
    @DeleteMapping("/me/delete")
    public String deleteUser(Authentication authentication) {
        userService.softDelete(authentication.getName());
        return "삭제 완료";
    }





}
