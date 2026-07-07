package com.sparta.orderproject.dto;

import com.sparta.orderproject.entity.UserRoleEnum;

import java.time.LocalDateTime;

public class UserRequestDto {
    private String username;
    private String password;

    private String nickname;

    private String phone;

    private String updated_by;

    private String deleted_by;

    private UserRoleEnum userRoleEnum;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;




}
