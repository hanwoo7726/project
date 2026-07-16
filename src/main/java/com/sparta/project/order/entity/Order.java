package com.sparta.project.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", length = 36)
    private String orderId;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStateEnum orderStatus;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "delivery_fee", nullable = false)
    private Integer deliveryFee;

    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;

    @Column(name = "final_amount", nullable = false)
    private Integer finalAmount;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "delivery_request")
    private String deliveryRequest;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;

    @Setter
    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Setter
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Setter
    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    // 생성일
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 생성자
    @Column(name = "created_by")
    private String createdBy;

    // 수정일
    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 수정자
    @Setter
    @Column(name = "updated_by")
    private String updatedBy;

    // 주문자
    @Column(name = "user_id")
    private String userId;

    // 가맹점
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 주문자
     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    /**
     * 가맹점
     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id", nullable = false)
//    private Store store;

    /**
     * 주문 상품 목록
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();

    /**
     * 주문 상태 변경 이력
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderStatusHistory> orderStatusHistories = new ArrayList<>();


}
