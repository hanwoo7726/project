package com.sparta.orderproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
