package com.sparta.project.product.controller;


import com.sparta.project.product.dto.response.ProductResponse;
import com.sparta.project.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    // 관리자 상품 목록 조회
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProductsForAdmin(
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10) Pageable pageable
    ){
        Page<ProductResponse> response = productService.getProductsForAdmin(storeId, keyword, pageable);
        return ResponseEntity.ok(response);
    }
}
