package com.sparta.project.order.repository;

import com.sparta.project.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByOrderIdAndUserId(String orderId, String userId);

    Page<Order> findByUserId(String userId, Pageable pageable);

    Page<Order> findByStoreId(Long storeId, Pageable pageable);

    Optional<Order> findByOrderIdAndStoreId(String orderId, Long storeId);
}
