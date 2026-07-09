package com.sparta.orderproject.service;

import com.sparta.orderproject.dto.LoginRequestDto;
import com.sparta.orderproject.dto.UserRequestDto;
import com.sparta.orderproject.dto.UserResponseDto;
import com.sparta.orderproject.entity.User;
import com.sparta.orderproject.entity.UserRoleEnum;
import com.sparta.orderproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserResponseDto createUser(UserRequestDto userRequestDto){
        User user = new User(userRequestDto.getUsername(),userRequestDto.getPassword()
        ,userRequestDto.getNickname(),userRequestDto.getPhone(), UserRoleEnum.ROLE_USER);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(user);


    }

    public List<UserResponseDto> findAllUsers(){
        List<User> user = userRepository.findAll();


        return user.stream()
                .map(UserResponseDto::new)
                .toList();

    }


    @Transactional
    public void updatePassword(Long id, String password){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 유저 입니다."));

        user.update(password);

    }

    @Transactional
    public void softDelete(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 유저 입니다."));

        user.softDelete(user.getNickname());
    }

    // 로그인 기능
    public String loginUser(LoginRequestDto dto){
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));

        if(!user.getPassword().equals(dto.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");


        }

        return "로그인 성공";




    }









}
