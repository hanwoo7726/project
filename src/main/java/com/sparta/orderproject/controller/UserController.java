package com.sparta.orderproject.controller;

import com.sparta.orderproject.entity.User;
import com.sparta.orderproject.repository.UserRepository;
import com.sparta.orderproject.security.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    // CRUD 기능 만들기
    // 유저 생성, 조회, 수정, 삭제

    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping
    public List<User> findAllUsers(){





        return userRepository.findAll();
    }














}
