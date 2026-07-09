package com.sparta.project.controller;

import com.sparta.project.dto.LoginRequestDto;
import com.sparta.project.dto.PasswordRequestDto;
import com.sparta.project.dto.UserRequestDto;
import com.sparta.project.dto.UserResponseDto;
import com.sparta.project.entity.User;
import com.sparta.project.repository.UserRepository;
import com.sparta.project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    // CRUD 기능 만들기
    // 유저 생성, 조회, 수정, 삭제

    private final UserRepository userRepository;
    private final UserService userService;


    // 전체 유저 조회
    @GetMapping("/findAll")
    public List<UserResponseDto> findAllUsers(){




        return userService.findAllUsers();
    }

    // 회원 가입
    @PostMapping("/create")
    public UserResponseDto createUser(@Valid  @RequestBody UserRequestDto userRequestDto){

        return userService.createUser(userRequestDto);

    }

    // 비밀번호 변경
    @PutMapping("/update/{id}")
    public String updatePassword(@PathVariable Long id,
                                 @Valid @RequestBody PasswordRequestDto dto){
        userService.updatePassword(id, dto.getPassword());




        return "변경 완료";

    }

    // 유저 삭제
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.softDelete(id);


        return "삭제 완료";
    }


    // 로그인

    @PostMapping
    public String loginUser(@Valid @RequestBody LoginRequestDto dto){
        userService.loginUser(dto);


        return "로그인 성공";

    }














}
