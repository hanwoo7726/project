package com.sparta.project.order.dto.response;

import com.sparta.project.order.entity.OrderProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductResponseDto {

    /**
     * 상품ID
     */
    private String productId;

    /**
     * 상품명
     */
    private String productName;

    /**
     * 수량
     */
    private Integer quantity;

    /**
     * 상품단가
     */
    private Integer unitPrice;

    /**
     * 총금액
     */
    private Integer totalPrice;

    public static OrderProductResponseDto from(OrderProduct orderProduct) {
        return OrderProductResponseDto.builder()
                .productId(orderProduct.getProductId().toString())
                .productName(orderProduct.getProductName())
                .quantity(orderProduct.getQuantity())
                .unitPrice(orderProduct.getUnitPrice())
                .totalPrice(orderProduct.getTotalPrice())
                .build();
    }
}