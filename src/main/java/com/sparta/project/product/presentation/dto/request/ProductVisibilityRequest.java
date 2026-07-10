package com.sparta.project.product.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

// 상품 노출-숨김 전환 요청
@Getter
public class ProductVisibilityRequest {
    @NotNull(message = "숨김 여부는 필수입니다. ")
    private Boolean isHidden;
}
