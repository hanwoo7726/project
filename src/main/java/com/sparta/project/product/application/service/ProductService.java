package com.sparta.project.product.application.service;

import com.sparta.project.product.domain.entity.Product;
import com.sparta.project.product.domain.repository.ProductRepository;
import com.sparta.project.product.presentation.dto.request.ProductCreateRequest;
import com.sparta.project.product.presentation.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request){
        // 1. 요청 DTO -> Entity 생성
        Product product = Product.create(
                request.getStoreId(),
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getDisplayOrder()
        );
        // 2. Save
        Product savedProduct = productRepository.save(product);
        // 3. Entity -> Response DTO 변환 후 반환
        return ProductResponse.from(savedProduct);
    }

    @Transactional
    public ProductResponse getProduct(UUID productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. "));
        return ProductResponse.from(product);
    }
}
