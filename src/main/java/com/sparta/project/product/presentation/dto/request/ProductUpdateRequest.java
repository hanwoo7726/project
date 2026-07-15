package com.sparta.project.product.presentation.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;

// 상품 수정 요청 DTO
@Getter
public class ProductUpdateRequest {

    @Size(max = 100, message = "상품명은 100자 이하여야 합니다.")
    private String name;

    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다. ")
    private Integer price;

    private String description;

    private Integer displayOrder;
}
