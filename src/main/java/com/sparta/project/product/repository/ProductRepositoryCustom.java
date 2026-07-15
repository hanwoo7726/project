package com.sparta.project.product.repository;

import com.sparta.project.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductRepositoryCustom {

    // 상품 검색 (조건 조합)
    Page<Product> searchProducts(UUID storeId, String keyword, Pageable pageable);

    // displayOrder 이상인 상품드의 순서를 1씩 밀기
    void shiftDisplayOrder(UUID storeId, Integer fromOrder );

    // 현재 최대 displayOrder 조회
    Integer findMaxDisplayOrder(UUID storeId);
}