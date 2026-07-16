package com.sparta.project.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDto {

    /**
     * 고객 ID
     */
    @NotBlank(message = "고객 ID는 필수입니다.")
    private String userId;

    /**
     * 주문할 가게
     */
    @NotBlank(message = "가게 ID는 필수입니다.")
    private String storeId;

    /**
     * 배송 주소
     */
    @NotBlank(message = "배송 주소는 필수입니다.")
    private String deliveryAddress;

    /**
     * 요청사항
     */
    private String deliveryRequest;

    /**
     * 주문 상품 목록
     */
    private List<OrderProductRequestDto> orderProducts;

}