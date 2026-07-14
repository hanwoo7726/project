package com.sparta.project.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String token;

    private LocalDateTime expiresAt;

    public RefreshToken(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public void update(String token) {
        this.token = token;


    }


}
