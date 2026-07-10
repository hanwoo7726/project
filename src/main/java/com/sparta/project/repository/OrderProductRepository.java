package com.sparta.project.repository;

import com.sparta.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<Order, String> {

}
