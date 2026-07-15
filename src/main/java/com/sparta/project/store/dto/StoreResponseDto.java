package com.sparta.project.store.dto;

import com.sparta.project.store.entity.Store;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreResponseDto {

    private Long id;
    private String ownerUsername;
    private String name;
    private String category;
    private String address;
    private String phone;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minimumOrderPrice;
    private Integer deliveryFee;
    private Double averageRating;
    private Integer reviewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static StoreResponseDto from(Store store) {
        return StoreResponseDto.builder()
                .id(store.getId())
                .ownerUsername(store.getOwnerUsername())
                .name(store.getName())
                .category(store.getCategory())
                .address(store.getAddress())
                .phone(store.getPhone())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .minimumOrderPrice(store.getMinimumOrderPrice())
                .deliveryFee(store.getDeliveryFee())
                .averageRating(store.getAverageRating())
                .reviewCount(store.getReviewCount())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }
}
