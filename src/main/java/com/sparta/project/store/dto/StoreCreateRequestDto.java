package com.sparta.project.store.dto;

import com.sparta.project.store.entity.Store;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class StoreCreateRequestDto {

    @NotBlank(message = "가맹점 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phone;

    @NotNull(message = "오픈시간은 필수입니다.")
    private LocalTime openTime;

    @NotNull(message = "마감시간은 필수입니다.")
    private LocalTime closeTime;

    @NotNull(message = "최소주문금액은 필수입니다.")
    @PositiveOrZero(message = "최소주문금액은 0 이상이어야 합니다.")
    private Integer minimumOrderPrice;

    @NotNull(message = "배달비는 필수입니다.")
    @PositiveOrZero(message = "배달비는 0 이상이어야 합니다.")
    private Integer deliveryFee;

    public Store toEntity(String ownerUsername) {
        return Store.builder()
                .ownerUsername(ownerUsername)
                .name(name)
                .category(category)
                .address(address)
                .phone(phone)
                .openTime(openTime)
                .closeTime(closeTime)
                .minimumOrderPrice(minimumOrderPrice)
                .deliveryFee(deliveryFee)
                .build();
    }
}
