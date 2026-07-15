package com.sparta.project.order.dto.response;

import com.sparta.project.order.entity.Order;
import com.sparta.project.order.entity.OrderStateEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderListResponseDto {

    private String orderId;
    private String orderNumber;
    private OrderStateEnum orderStatus;
    private Integer finalAmount;
    private LocalDateTime createdAt;

    public static OrderListResponseDto from(Order order) {

        return OrderListResponseDto.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .finalAmount(order.getFinalAmount())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
