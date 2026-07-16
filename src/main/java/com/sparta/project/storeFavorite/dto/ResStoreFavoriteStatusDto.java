package com.sparta.project.storeFavorite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResStoreFavoriteStatusDto {

  private Long userId;
  private Long storeId;
  private boolean favorite;


  public static ResStoreFavoriteStatusDto favorited(Long userId, Long storeId) {
    return new ResStoreFavoriteStatusDto(userId, storeId, true);
  }


  public static ResStoreFavoriteStatusDto removed(Long userId, Long storeId) {
    return new ResStoreFavoriteStatusDto(userId, storeId, false);
  }


}
