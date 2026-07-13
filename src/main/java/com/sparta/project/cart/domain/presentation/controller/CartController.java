package com.sparta.project.cart.domain.presentation.controller;

import com.sparta.project.cart.domain.application.service.CartService;
import com.sparta.project.cart.domain.presentation.dto.request.ReqAddCartItemDto;
import com.sparta.project.cart.domain.presentation.dto.response.ResCartDto;
import com.sparta.project.global.infrastructure.presentation.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

  private final CartService cartService;

  // 장바구니 아이템 추가
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
}
