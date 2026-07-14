package com.sparta.project.product.service;

import com.sparta.project.product.entity.Product;
import com.sparta.project.product.repository.ProductRepository;
import com.sparta.project.product.dto.request.ProductCreateRequest;
import com.sparta.project.product.dto.request.ProductUpdateRequest;
import com.sparta.project.product.dto.response.ProductResponse;
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
    private final ProductAiService productAiService;


    // 상품 등록
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        // 1. displayOrder 계산
        Integer displayOrder = request.getDisplayOrder();
        if(displayOrder == null){
            displayOrder = productRepository.findMaxDisplayOrder(request.getStoreId()) + 1;
        }
        else{
            productRepository.shiftDisplayOrder(request.getStoreId(), displayOrder);
        }

        // 2. 상품 먼저 생성
        String description = request.getDescription();

        Product product = Product.create(
                request.getStoreId(),
                request.getName(),
                request.getPrice(),
                description,
                displayOrder
        );
        Product savedProduct = productRepository.save(product);

        // 3. AI 생성 요청이면 설명 생성해서 갱신
        if(Boolean.TRUE.equals(request.getAiGenerate())){
            String aiDescription = productAiService.generateDescription(
                    savedProduct.getProductId(),
                    request.getAiPrompt(),
                    null    // createdBy용
            );
            if(aiDescription != null){
                savedProduct.updateDescription(aiDescription);
            }
        }

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

    // 상품 숨김/노출 전환
    @Transactional
    public ProductResponse updateVisibility(UUID productId, Boolean isHidden){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("상품을 찾을 수 없습니다. "));

        if (isHidden){
            product.hide();
        }
        else{
            product.show();
        }

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
