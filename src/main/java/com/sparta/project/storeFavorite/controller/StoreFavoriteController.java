package com.sparta.project.storeFavorite.controller;

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
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    List<ResStoreFavoriteDto> resStoreFavoriteDtoList =
        storeFavoriteService.getFavorites(userDetails.getUserId());

    return ResponseEntity.status(HttpStatus.OK)
        .body(resStoreFavoriteDtoList);
  }

  @PutMapping("/{storeId}")
  public ResponseEntity<ResStoreFavoriteStatusDto> addFavorite(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable UUID storeId
  ) {
    ResStoreFavoriteStatusDto resDto = storeFavoriteService.addFavorite(userDetails.getUserId(), storeId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(resDto);
  }

  @DeleteMapping("/{storeId}")
  public ResponseEntity<ResStoreFavoriteStatusDto> removeFavorite(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable UUID storeId
  ) {
    ResStoreFavoriteStatusDto resDto = storeFavoriteService.removeFavorite(userDetails.getUserId(), storeId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(resDto);
  }
}
