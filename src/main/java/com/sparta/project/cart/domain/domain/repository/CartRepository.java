package com.sparta.project.cart.domain.domain.repository;

import com.sparta.project.cart.domain.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

}
