package com.sparta.project.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

    @NotNull(message = "가게 ID는 필수입니다.")
    private Long storeId;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @PositiveOrZero(message = "가격은 0이상이어야 합니다.")
    private Integer price;

    private String description;

    private Integer displayOrder;

    private Boolean aiGenerate; // AI 생성 여부

    @Size(max = 100, message = "AI 프롬프트는 100자 이하여야 합니다. ")
    private String aiPrompt; // AI 프롬프트
}
