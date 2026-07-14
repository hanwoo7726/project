package com.sparta.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDto {

    /**
     * 주문할 가게
     */
    private String storeId;

    /**
     * 배송 주소
     */
    private String deliveryAddress;

    /**
     * 요청사항
     */
    private String deliveryRequest;

    /**
     * 장바구니 주문 여부
     */
    private boolean cartOrder;

    /**
     * 주문 상품 목록
     */
    private List<OrderProductRequestDto> orderProducts;

}