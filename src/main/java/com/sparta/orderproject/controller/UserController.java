package com.sparta.orderproject.controller;

import com.sparta.orderproject.dto.PasswordRequestDto;
import com.sparta.orderproject.dto.UserRequestDto;
import com.sparta.orderproject.dto.UserResponseDto;
import com.sparta.orderproject.entity.User;
import com.sparta.orderproject.repository.UserRepository;
import com.sparta.orderproject.service.UserService;
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
    public String loginUser()














}
