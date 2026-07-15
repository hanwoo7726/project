package com.sparta.project.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_order_product")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = "order_item_id", length = 36)
    private UUID orderItemId;

    /**
     * 상품 ID
     * Product 엔티티와 연관관계를 맺지 않고
     * 주문 당시 상품 ID만 저장
     */
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "product_id", length = 36)
    private UUID productId;

    /**
     * 주문 당시 상품명
     */
    @Column(name = "product_name", nullable = false)
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
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}
