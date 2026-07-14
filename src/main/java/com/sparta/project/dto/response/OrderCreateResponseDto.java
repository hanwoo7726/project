package com.sparta.project.dto.response;

import com.sparta.project.entity.OrderStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class OrderCreateResponseDto {

    private String orderId;

    private String orderNumber;

    private OrderStateEnum orderStatus;

    private Integer finalAmount;

    private LocalDateTime orderedAt;

}
