package com.sparta.project.order.repository;

import com.sparta.project.order.entity.Order;
import com.sparta.project.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, String> {

    List<OrderProduct> findByOrder(Order order);

}
