package com.sparta.orderproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_user")
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "아이디는 필수 입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입니다.")
    private String nickname;

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-0000-0000 이어야 합니다.")
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRoleEnum;


    public User(Long id, String username, String password, String nickname, String phone, UserRoleEnum userRoleEnum) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.userRoleEnum = userRoleEnum;
    }
}
