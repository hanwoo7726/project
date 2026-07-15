package com.sparta.project.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_order_status_history")
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = "order_hist_id", length = 36)
    private UUID historyId;

    /**
     * 주문
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * 이전 주문 상태
     */
    @Column(name = "order_number")
    private String orderNumber;

    /**
     * 변경된 주문 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStateEnum orderStatus;

    /**
     * etc
     */
    @Column(name = "etc", nullable = false)
    private String etc;

    /**
     * 생성일
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 생성자
     */
    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    /**
     * 수정일
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 수정자
     */
    @Column(name = "updated_by", nullable = false, length = 100)
    private String updatedBy;
}