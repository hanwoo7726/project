package com.sparta.project.payment.repository;

import com.sparta.project.order.entity.Order;
import com.sparta.project.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByOrder(Order order);

    Optional<Payment> findByPaymentKey(String paymentKey);

}
