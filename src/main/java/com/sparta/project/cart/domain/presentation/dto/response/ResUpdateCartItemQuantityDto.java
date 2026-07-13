package com.sparta.project.cart.domain.presentation.dto.response;

import com.sparta.project.cart.domain.domain.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResUpdateCartItemQuantityDto {

  private UUID cartItemId;
  private UUID productId;
  private String productName;
  private int quantity;


  public static ResUpdateCartItemQuantityDto from(CartItem cartItem) {
    return new ResUpdateCartItemQuantityDto(
        cartItem.getId(),
        cartItem.getProduct().getId(),
        cartItem.getProduct().getName(),
        cartItem.getQuantity()
    );
  }
}
