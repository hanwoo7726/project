package com.sparta.project.product.application.service;

import com.sparta.project.product.domain.entity.Product;
import com.sparta.project.product.domain.repository.ProductRepository;
import com.sparta.project.product.presentation.dto.request.ProductCreateRequest;
import com.sparta.project.product.presentation.dto.request.ProductUpdateRequest;
import com.sparta.project.product.presentation.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    // 페이지 크기
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final List<Integer> ALLOWED_PAGE_SIZES = List.of(10, 20, 30);


    // 상품 등록
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
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

    // 상품 단건 조회
    @Transactional(readOnly = true)
    public ProductResponse getProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. "));
        return ProductResponse.from(product);
    }

    // 상품 목록 조회
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(UUID storeId, String keyword, Pageable pageable) {
        Pageable validatedPageable = validatePageSize(pageable);

        return productRepository.searchProducts(storeId, keyword, validatedPageable)
                .map(ProductResponse::from);
    }

    // 상품 수정
    public ProductResponse updateProduct(UUID productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("상품을 찾을 수 없습니다. "));

        product.update(
               request.getName(),
               request.getPrice(),
               request.getDescription(),
               request.getDisplayOrder()
        );

        return ProductResponse.from(product);
    }

    // 페이지 크기 검증  10/20/30
    private Pageable validatePageSize(Pageable pageable){
        int size = pageable.getPageSize();

        if(ALLOWED_PAGE_SIZES.contains(size)){
            return PageRequest.of(pageable.getPageNumber(), DEFAULT_PAGE_SIZE, pageable.getSort());
        }
        return pageable;
    }
}
