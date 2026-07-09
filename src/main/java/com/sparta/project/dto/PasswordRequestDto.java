package com.sparta.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordRequestDto {

    @NotBlank(message = "새 비밀번호는 필수입니다.")
    @Pattern(
            regexp = "^[A-Za-z0-9!@#$%^&*]{8,20}$",
            message = "비밀번호는 8자 이상 20자 이하이며 영어, 숫자, 특수문자만 가능합니다."
    )
    private String password;
}
