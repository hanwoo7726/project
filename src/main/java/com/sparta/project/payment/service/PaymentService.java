package com.sparta.project.payment.service;

import com.sparta.project.order.entity.Order;
import com.sparta.project.order.repository.OrderRepository;
import com.sparta.project.payment.dto.PaymentResponseDto;
import com.sparta.project.payment.entity.Payment;
import com.sparta.project.payment.entity.PaymentMethod;
import com.sparta.project.payment.entity.PaymentStatus;
import com.sparta.project.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public PaymentResponseDto payAccess(String orderId) {
        Optional<Order> optional = orderRepository.findById(orderId);

        System.out.println("orderId = " + orderId);
        System.out.println("조회 결과 = " + optional.isPresent());

        if (optional.isPresent()) {
            System.out.println(optional.get().getOrderId());
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        paymentRepository.findByOrder(order)
                .ifPresent(payment -> {
                    throw new IllegalStateException("이미 결제가 완료된 주문입니다.");
                });

        Payment payment = Payment.builder()
                .order(order)
                .paymentKey(UUID.randomUUID().toString())
                .paymentMethod(PaymentMethod.CARD)
                .amount(order.getFinalAmount())
                .paymentStatus(PaymentStatus.READY)
                .approvedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .createdBy(order.getUserId())
                .updatedAt(LocalDateTime.now())
                .updatedBy(order.getUserId())
                .build();

        payment.complete();

        paymentRepository.save(payment);

        return PaymentResponseDto.builder()
                .paymentId(payment.getPaymentId())
                .paymentKey(payment.getPaymentKey())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .approvedAt(payment.getApprovedAt())
                .build();
    }

    @Transactional
    public void cancel(String paymentId){

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 내역이 없습니다."));

        payment.cancel();
    }
}