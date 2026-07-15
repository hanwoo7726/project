package com.sparta.project.order.dto.response;

import com.sparta.project.order.entity.Order;
import com.sparta.project.order.entity.OrderStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private String orderId;
    private String orderNumber;
    private OrderStateEnum orderStatus;
    private Integer totalPrice;
    private Integer deliveryFee;
    private Integer discountAmount;
    private Integer finalAmount;
    private String deliveryAddress;
    private String deliveryRequest;

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .deliveryFee(order.getDeliveryFee())
                .discountAmount(order.getDiscountAmount())
                .finalAmount(order.getFinalAmount())
                .deliveryAddress(order.getDeliveryAddress())
                .deliveryRequest(order.getDeliveryRequest())
                .build();
    }
}
