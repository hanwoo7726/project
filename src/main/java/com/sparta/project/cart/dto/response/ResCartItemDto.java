package com.sparta.project.cart.dto.response;

import com.sparta.project.cart.entity.CartItem;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ResCartItemDto {

  private UUID cartItemId;
  private UUID productId;
  private String productName;
  private int productPrice;
  private int quantity;
  private int itemTotalPrice;

  public ResCartItemDto(CartItem cartItem) {
    Product product = cartItem.getProduct();

    this.cartItemId = cartItem.getId();
    this.productId = product.getId();
    this.productName = product.getName();
    this.productPrice = product.getPrice();
    this.quantity = cartItem.getQuantity();
    this.itemTotalPrice = cartItem.cartItemPrice();
  }

  public static ResCartItemDto from(CartItem cartItem) {
    return new ResCartItemDto(cartItem);
  }
}
