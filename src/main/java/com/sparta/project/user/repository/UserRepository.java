package com.sparta.project.user.repository;

import com.sparta.project.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);

        Optional<User> findByUsernameAndDeletedAtIsNull(String name);

        List<User> findAllByDeletedAtIsNull();


        boolean existsByUsername(String username);

        boolean existsByNickname(String nickname);




}
