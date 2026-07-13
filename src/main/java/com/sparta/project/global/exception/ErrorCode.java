package com.sparta.project.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  CART_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),
  CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니 상품을 찾을 수 없습니다."),
  CART_STORE_CONFLICT(HttpStatus.CONFLICT, "장바구니에 다른 가게의 상품이 담겨 있습니다."),
  QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "수량은 1개 이상이어야 합니다.");


  private final HttpStatus status;
  private final String message;

  ErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
