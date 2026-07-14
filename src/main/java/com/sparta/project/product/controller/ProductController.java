package com.sparta.project.product.controller;

import com.sparta.project.product.service.ProductService;
import com.sparta.project.product.ai.GeminiClient;
import com.sparta.project.product.dto.request.ProductCreateRequest;
import com.sparta.project.product.dto.request.ProductUpdateRequest;
import com.sparta.project.product.dto.request.ProductVisibilityRequest;
import com.sparta.project.product.dto.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final GeminiClient geminiClient;

    // 상품 등록
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request
    ){
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 상품 단건 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID productId){
        ProductResponse response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam(required = false) UUID storeId,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10) Pageable pageable
    ) {
                Page<ProductResponse> response = productService.getProducts(storeId, keyword, pageable);
                return ResponseEntity.ok(response);
    }

    // 상품 수정
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductUpdateRequest request
    ){
        ProductResponse response = productService.updateProduct(productId, request);
        return ResponseEntity.ok(response);
    }

    // 상품 숨김-노출 전환
    @PatchMapping("/{productId}/visibility")
    public ResponseEntity<ProductResponse> updateVisibility(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductVisibilityRequest request
    ){
        ProductResponse response = productService.updateVisibility(productId, request.getIsHidden());

        return ResponseEntity.ok(response);
    }

    // (ai 생성 임시 테스트용)
    @GetMapping("/ai-test")
    public ResponseEntity<String> aiTest(@RequestParam String prompt){
        return ResponseEntity.ok(geminiClient.generate(prompt));
    }
}
