package com.sparta.project.product.domain.repository;

import com.sparta.project.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductRepositoryCustom {

    // 상품 검색 (조건 조합)
    Page<Product> searchProducts(UUID storeId, String keyword, Pageable pageable);
}