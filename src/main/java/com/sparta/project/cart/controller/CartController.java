package com.sparta.project.cart.controller;

import com.sparta.project.cart.service.CartService;
import com.sparta.project.cart.dto.request.ReqAddCartItemDto;
import com.sparta.project.cart.dto.request.ReqUpdateCartItemQuantityDto;
import com.sparta.project.cart.dto.response.ResCartDto;
import com.sparta.project.cart.dto.response.ResUpdateCartItemQuantityDto;
import com.sparta.project.global.infrastructure.presentation.ApiResponse;
import com.sparta.project.user.security.PrincipalDetails;
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
      @AuthenticationPrincipal PrincipalDetails userDetails,
      @RequestBody @Valid ReqAddCartItemDto reqDto
  ) {
    ResCartDto cartDto = cartService.addCartItem(userDetails.getUser().getId(), reqDto);

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
      @AuthenticationPrincipal PrincipalDetails userDetails
  ) {
    ResCartDto cartDto = cartService.getCart(userDetails.getUser().getId());

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
      @AuthenticationPrincipal PrincipalDetails userDetails,
      @PathVariable UUID cartItemId,
      @RequestBody @Valid ReqUpdateCartItemQuantityDto reqDto
  ) {

    ResUpdateCartItemQuantityDto resDto = cartService.updateCartItemQuantity(
        userDetails.getUser().getId(),
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
      @AuthenticationPrincipal PrincipalDetails userDetails,
      @PathVariable UUID cartItemId
  ) {
    cartService.deleteCartItem(userDetails.getUser().getId(), cartItemId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(ApiResponse.success(HttpStatus.OK));
  }

  //장바구니 아이템 전체 비우기
  @DeleteMapping
  public ResponseEntity<ApiResponse<Void>> clearCart(
      @AuthenticationPrincipal PrincipalDetails userDetails
  ) {
    cartService.clearCart(userDetails.getUser().getId());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(ApiResponse.success(HttpStatus.OK));
  }


}
