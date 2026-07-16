package com.sparta.project.product.repository;

import com.sparta.project.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    // 상품 검색 (조건 조합)
    Page<Product> searchProducts(Long storeId, String keyword, Pageable pageable, boolean includeDeleted);

    // displayOrder 이상인 상품드의 순서를 1씩 밀기
    void shiftDisplayOrder(Long storeId, Integer fromOrder );

    // 현재 최대 displayOrder 조회
    Integer findMaxDisplayOrder(Long storeId);
}