package com.sparta.project.user.controller;

import com.sparta.project.user.dto.LoginRequestDto;
import com.sparta.project.user.dto.PasswordRequestDto;
import com.sparta.project.user.dto.UserRequestDto;
import com.sparta.project.user.dto.UserResponseDto;
import com.sparta.project.user.repository.UserRepository;
import com.sparta.project.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @GetMapping()
    public List<UserResponseDto> findAllUsers(){




        return userService.findAllUsers();
    }

    // 회원 가입
    @PostMapping()
    public UserResponseDto createUser(@Valid  @RequestBody UserRequestDto userRequestDto){

        return userService.createUser(userRequestDto);

    }

    // 비밀번호 변경
    @PutMapping("/{id}")
    public String updatePassword(@PathVariable Long id,
                                 @Valid @RequestBody PasswordRequestDto dto){
        userService.updatePassword(id, dto.getPassword());




        return "변경 완료";

    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.softDelete(id);


        return "삭제 완료";
    }


    // 로그인

    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody LoginRequestDto dto){



        return userService.loginUser(dto);

    }














}
