package com.sparta.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_order_product")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_item_id", length = 30)
    private String orderItemId;

    /**
     * 상품 ID
     * Product 엔티티와 연관관계를 맺지 않고
     * 주문 당시 상품 ID만 저장
     */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /**
     * 주문 당시 상품명
     */
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    /**
     * 주문 수량
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * 주문 당시 상품 가격
     */
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    /**
     * 총 금액
     */
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", nullable = false, length = 100)
    private String updatedBy;

    /**
     * 주문
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * 상품
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
