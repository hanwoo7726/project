package com.sparta.project.payment.dto;

import com.sparta.project.payment.entity.PaymentMethod;
import com.sparta.project.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponseDto {
    private String paymentId;
    private String paymentKey;
    private Integer amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime approvedAt;
    private LocalDateTime canceledAt;
    private LocalDateTime createdAt;
    private LocalDateTime createdBy;
    private LocalDateTime updatedAt;
    private LocalDateTime updatedBy;
}
