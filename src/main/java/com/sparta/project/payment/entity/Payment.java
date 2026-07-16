package com.sparta.project.payment.entity;

import com.sparta.project.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", length = 36)
    private String paymentId;

    /**
     * 주문
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    /**
     * 결제 고유번호
     * (실제 PG의 paymentKey 역할)
     */
    @Column(nullable = false, unique = true)
    private String paymentKey;

    /**
     * 카드 결제
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    /**
     * 결제 금액
     */
    @Column(nullable = false)
    private Integer amount;

    /**
     * 결제 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    /**
     * 결제 승인 시간
     */
    private LocalDateTime approvedAt;

    /**
     * 생성일
     */
    private LocalDateTime createdAt;

    /**
     * 생성자
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 수정일
     */
    private LocalDateTime updatedAt;

    /**
     * 수정자
     */
    @Column(name = "updated_by")
    private String updatedBy;

    public void complete() {
        this.paymentStatus = PaymentStatus.COMPLETED;
        this.approvedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.paymentStatus = PaymentStatus.CANCELED;
    }
}