package com.sparta.project.product.presentation.controller;

import com.sparta.project.product.application.service.ProductService;
import com.sparta.project.product.presentation.dto.request.ProductCreateRequest;
import com.sparta.project.product.presentation.dto.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
                Page<ProductResponse> response = productService.getProducts(pageable);
                return ResponseEntity.ok(response);
    }
}
