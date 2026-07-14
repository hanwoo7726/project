package com.sparta.project.user.service;

import com.sparta.project.user.dto.LoginRequestDto;
import com.sparta.project.user.dto.UserRequestDto;
import com.sparta.project.user.dto.UserResponseDto;
import com.sparta.project.user.entity.User;
import com.sparta.project.user.entity.UserRoleEnum;
import com.sparta.project.user.jwt.JwtUtil;
import com.sparta.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    // 회원 가입
    public UserResponseDto createUser(UserRequestDto userRequestDto){

        if(userRepository.existsByUsername(userRequestDto.getUsername())){
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        if(userRepository.existsByNickname(userRequestDto.getNickname())){
            throw new RuntimeException("이미 존재하는 닉네임입니다.");

        }




        String encodePassword = passwordEncoder.encode(userRequestDto.getPassword());

        User user = new User(userRequestDto.getUsername(),encodePassword
        ,userRequestDto.getNickname(),userRequestDto.getPhone(), UserRoleEnum.USER);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);


    }

    // 회원 목록 전체 조회
    public List<UserResponseDto> findAllUsers(){
        List<User> user = userRepository.findAllByDeletedAtIsNull();


        return user.stream()
                .map(UserResponseDto::new)
                .toList();

    }


    // 회원 정보 변경
    @Transactional
    public void updatePassword(String username, String password){
        User user = userRepository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() -> new RuntimeException("없는 유저 입니다."));

        String encodedPassword = passwordEncoder.encode(password);
        user.update(encodedPassword);

    }

    // 회원 탈퇴 기능
    @Transactional
    public void softDelete(String username){
        User user = userRepository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() -> new RuntimeException("없는 유저 입니다."));

        user.softDelete(username);
    }

    // 로그인 기능
    public String loginUser(LoginRequestDto dto){
        User user = userRepository.findByUsernameAndDeletedAtIsNull(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");


        }

        return jwtUtil.createToken(user.getUsername(), user.getUserRoleEnum());




    }









}
