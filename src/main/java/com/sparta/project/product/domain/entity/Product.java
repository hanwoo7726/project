package com.sparta.project.product.domain.entity;

import com.sparta.project.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "p_product")
@Getter // Setter 없이 Getter만
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA용 기본 생성자(외부 생성 방지)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "store_id", nullable = false)
    private UUID storeId; // 직접 생성

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden = false;

    @Column(name = "display_order")
    private Integer displayOrder;

}

