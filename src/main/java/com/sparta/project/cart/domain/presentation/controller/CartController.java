package com.sparta.project.cart.domain.presentation.controller;

import com.sparta.project.cart.domain.application.service.CartService;
import com.sparta.project.cart.domain.presentation.dto.request.ReqAddCartItemDto;
import com.sparta.project.cart.domain.presentation.dto.request.ReqUpdateCartItemQuantityDto;
import com.sparta.project.cart.domain.presentation.dto.response.ResCartDto;
import com.sparta.project.cart.domain.presentation.dto.response.ResUpdateCartItemQuantityDto;
import com.sparta.project.global.infrastructure.presentation.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

  private final CartService cartService;

  // 장바구니 아이템 추가
  @PostMapping("/items")
  public ResponseEntity<ApiResponse<ResCartDto>> addItemToCart(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody @Valid ReqAddCartItemDto reqDto
  ) {
    ResCartDto cartDto = cartService.addCartItem(userDetails.getUserId(), reqDto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success(
            HttpStatus.CREATED,
            cartDto
        ));
  }


  // 장바구니 보기
  @GetMapping
  public ResponseEntity<ApiResponse<ResCartDto>> getCart(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    ResCartDto cartDto = cartService.getCart(userDetails.getUserId());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(ApiResponse.success(
            HttpStatus.OK,
            cartDto
        ));
  }


  // 장바구니 수량 수정
  @PatchMapping("/{cartItemId}")
  public ResponseEntity<ApiResponse<ResUpdateCartItemQuantityDto>> updateCartItemQuantity(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable UUID cartItemId,
      @RequestBody @Valid ReqUpdateCartItemQuantityDto reqDto
  ) {

    ResUpdateCartItemQuantityDto resDto = cartService.updateCartItemQuantity(
        userDetails.getUserId(),
        cartItemId,
        reqDto.getQuantity()
    );

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(ApiResponse.success(
            HttpStatus.OK,
            resDto
        ));
  }


  //장바구니 아이템 삭제
  @DeleteMapping("/{cartItemId}")
  public ResponseEntity<ApiResponse<Void>> deleteCartItem(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable UUID cartItemId
  ) {
    cartService.deleteCartItem(userDetails.getUserId(), cartItemId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(ApiResponse.success(HttpStatus.OK));
  }

  //장바구니 아이템 전체 비우기
  @DeleteMapping
  public ResponseEntity<ApiResponse<Void>> clearCart(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    cartService.clearCart(userDetails.getUserId());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(ApiResponse.success(HttpStatus.OK));
  }


}
