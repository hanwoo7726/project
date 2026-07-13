package com.sparta.project.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;


@Getter
public class UserRequestDto {
    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(
            regexp = "^[a-z0-9]{4,10}$",
            message = "아이디는 4자 이상 10자 이하의 영어 소문자와 숫자만 가능합니다."
    )
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(
            regexp = "^[A-Za-z0-9!@#$%^&*]{8,20}$",
            message = "비밀번호는 8자 이상 20자 이하이며 영어, 숫자, 특수문자만 가능합니다."
    )
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]{2,10}$",
            message = "닉네임은 2자 이상 10자 이하의 한글, 영어, 숫자만 가능합니다."
    )
    private String nickname;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(
            regexp = "^010-\\d{4}-\\d{4}$",
            message = "전화번호 형식은 010-0000-0000 이어야 합니다."
    )
    private String phone;






}
