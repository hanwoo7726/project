package com.sparta.orderproject.dto;

import com.sparta.orderproject.entity.User;
import com.sparta.orderproject.entity.UserRoleEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String phone;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;

    private UserRoleEnum userRoleEnum;


    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
        this.createdAt = user.getCreatedAt();
        this.createdBy = user.getCreatedBy();
        this.updatedAt = user.getUpdatedAt();
        this.updatedBy = user.getUpdatedBy();
        this.deletedAt = user.getDeletedAt();
        this.deletedBy = user.getDeletedBy();
        this.userRoleEnum = user.getUserRoleEnum();
    }
}
