package com.sparta.project.storeFavorite.dto;

import com.sparta.project.storeFavorite.entity.StoreFavorite;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResStoreFavoriteDto {
  private Long userId;
  private UUID storeId;
  private String storeName;


  public static ResStoreFavoriteDto from(StoreFavorite storeFavorite) {
    return new ResStoreFavoriteDto(
        storeFavorite.getUser().getId(),
        storeFavorite.getStore().getId(),
        storeFavorite.getStore().getName()
    );
  }
}
