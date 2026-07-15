package com.sparta.project.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequestDto {

    /**
     * 상품 ID
     */
    private UUID productId;

    /**
     * 주문 수량
     */
    private Integer quantity;

}
