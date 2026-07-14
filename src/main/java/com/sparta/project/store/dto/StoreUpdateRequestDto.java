package com.sparta.project.store.dto;

import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalTime;
import lombok.Getter;

// PATCH 부분 수정: 전달된 필드만 반영하므로 모든 필드가 선택값이다.
@Getter
public class StoreUpdateRequestDto {

    private String name;

    private String category;

    private String address;

    private String phone;

    private LocalTime openTime;

    private LocalTime closeTime;

    @PositiveOrZero(message = "최소주문금액은 0 이상이어야 합니다.")
    private Integer minimumOrderPrice;

    @PositiveOrZero(message = "배달비는 0 이상이어야 합니다.")
    private Integer deliveryFee;
}
