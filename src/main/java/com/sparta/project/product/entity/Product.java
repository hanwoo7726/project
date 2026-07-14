package com.sparta.project.product.entity;

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

    // 상품 생성
    public static Product create(UUID storeId, String name, Integer price, String description, Integer displayOrder) {
        Product product = new Product();
        product.storeId = storeId;
        product.name = name;
        product.price = price;
        product.description = description;
        product.displayOrder = displayOrder;
        product.isHidden = false; // 이건 고민
        return product;
    }

    // 상품 정보 수정
    public void update(String name, Integer price, String description, Integer displayOrder) {
        if(name != null){
            this.price = price;
        }
        if(price != null){
            this.price = price;
        }
        if(description != null){
            this.description = description;
        }
        if(displayOrder != null){
            this.displayOrder = displayOrder;
        }
    }

    // 상품 숨김 처리
    public void hide(){
        this.isHidden = true;
    }
    // 상품 노출 처리
    public void show(){
        this.isHidden = false;
    }

    // AI 생성 설명 반영
    public void updateDescription(String description){
        this.description = description;
    }
}

