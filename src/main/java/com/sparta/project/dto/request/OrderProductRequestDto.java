package com.sparta.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequestDto {

    /**
     * 상품 ID
     */
    private String productId;

    /**
     * 주문 수량
     */
    private Integer quantity;

}
