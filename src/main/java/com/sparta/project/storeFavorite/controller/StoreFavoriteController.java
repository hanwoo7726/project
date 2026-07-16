package com.sparta.project.storeFavorite.controller;

import com.sparta.project.auth.security.PrincipalDetails;
import com.sparta.project.storeFavorite.service.StoreFavoriteService;
import com.sparta.project.storeFavorite.dto.ResStoreFavoriteDto;
import com.sparta.project.storeFavorite.dto.ResStoreFavoriteStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store_favorites")
public class StoreFavoriteController {

  private final StoreFavoriteService storeFavoriteService;

  @GetMapping
  public ResponseEntity<List<ResStoreFavoriteDto>> getFavorites(
      @AuthenticationPrincipal PrincipalDetails userDetails
  ) {
    List<ResStoreFavoriteDto> resStoreFavoriteDtoList =
        storeFavoriteService.getFavorites(userDetails.getUser().getId());

    return ResponseEntity.status(HttpStatus.OK)
        .body(resStoreFavoriteDtoList);
  }

  @PutMapping("/{storeId}")
  public ResponseEntity<ResStoreFavoriteStatusDto> addFavorite(
      @AuthenticationPrincipal PrincipalDetails userDetails,
      @PathVariable Long storeId
  ) {
    ResStoreFavoriteStatusDto resDto = storeFavoriteService.addFavorite(userDetails.getUser().getId(), storeId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(resDto);
  }

  @DeleteMapping("/{storeId}")
  public ResponseEntity<ResStoreFavoriteStatusDto> removeFavorite(
      @AuthenticationPrincipal PrincipalDetails userDetails,
      @PathVariable UUID storeId
  ) {
    ResStoreFavoriteStatusDto resDto = storeFavoriteService.removeFavorite(userDetails.getUser().getId(), storeId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(resDto);
  }
}
