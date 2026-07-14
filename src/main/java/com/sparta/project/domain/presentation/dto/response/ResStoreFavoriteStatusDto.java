package com.sparta.project.domain.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResStoreFavoriteStatusDto {

  private Long userId;
  private UUID storeId;
  private boolean favorite;


  public static ResStoreFavoriteStatusDto favorited(Long userId, UUID storeId) {
    return new ResStoreFavoriteStatusDto(userId, storeId, true);
  }


  public static ResStoreFavoriteStatusDto removed(Long userId, UUID storeId) {
    return new ResStoreFavoriteStatusDto(userId, storeId, false);
  }


}
