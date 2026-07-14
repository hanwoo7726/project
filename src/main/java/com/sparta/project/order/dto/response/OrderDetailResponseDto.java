package com.sparta.project.order.dto.response;

import com.sparta.project.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderDetailResponseDto {

    /**
     * 주문 ID
     */
    private String orderId;

    /**
     * 주문번호
     */
    private String orderNumber;

    /**
     * 주문상태
     */
    private String orderStatus;

    /**
     * 주문금액
     */
    private Integer totalPrice;

    /**
     * 배달비
     */
    private Integer deliveryFee;

    /**
     * 할인금액
     */
    private Integer discountAmount;

    /**
     * 최종결제금액
     */
    private Integer finalAmount;

    /**
     * 배달주소
     */
    private String deliveryAddress;

    /**
     * 배달요청사항
     */
    private String deliveryRequest;

    /**
     * 주문일시
     */
    private LocalDateTime createdAt;

    /**
     * 주문상품 목록
     */
    private List<OrderProductResponseDto> orderProducts;

    public static OrderDetailResponseDto from(Order order, List<OrderProductResponseDto> orderProducts) {
        return OrderDetailResponseDto.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus().name())
                .totalPrice(order.getTotalPrice())
                .deliveryFee(order.getDeliveryFee())
                .discountAmount(order.getDiscountAmount())
                .finalAmount(order.getFinalAmount())
                .deliveryAddress(order.getDeliveryAddress())
                .deliveryRequest(order.getDeliveryRequest())
                .createdAt(order.getCreatedAt())
                .orderProducts(orderProducts)
                .build();
    }

}
