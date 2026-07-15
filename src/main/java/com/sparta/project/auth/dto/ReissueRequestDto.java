package com.sparta.project.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReissueRequestDto {

    @NotBlank(message = "Refresh Token을 입력해주세요.")
    private String refreshToken;

}
