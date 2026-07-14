package com.sparta.project.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_user")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "아이디는 필수 입니다.")
    @Pattern(
            regexp = "^[a-z0-9]{4,10}$",
            message = "아이디는 4자 이상 10자 이하의 영어 소문자와 숫자만 가능합니다."
    )
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "비밀번호는 필수 입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입니다.")
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]{2,10}$",
            message = "닉네임은 2자 이상 10자 이하의 한글, 영어, 숫자만 가능합니다."
    )
    private String nickname;

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-0000-0000 이어야 합니다.")
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRoleEnum;


    public User(String username, String password, String nickname, String phone, UserRoleEnum userRoleEnum) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.userRoleEnum = userRoleEnum;
    }


    public void update(String password){
        this.password = password;
    }





}
