package com.sparta.project.product.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductCreateRequest {

    @NotNull(message = "가게 ID는 필수입니다.")
    private UUID storeId;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @PositiveOrZero(message = "가격은 0이상이어야 합니다.")
    private Integer price;

    private String description;

    private Integer displayOrder;
}
