package com.sparta.project.cart.dto.response;

import com.sparta.project.cart.entity.Cart;
import com.sparta.project.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResCartDto {

  private UUID cartId;
  private Long storeId;
  private String storeName;
  private int totalPrice;
  private List<ResCartItemDto> items;


  public static ResCartDto from(Cart cart) {
    Store store = cart.getStore();

    return new ResCartDto(
        cart.getId(),
        store != null ? store.getId() : null,
        store != null ? store.getName() : null,
        cart.totalPrice(),
        cart.getCartItems().stream()
            .map(ResCartItemDto::new)
            .toList());
  }


  public static ResCartDto empty() {
    return new ResCartDto(
        null,
        null,
        null,
        0,
        List.of()
    );
  }
}
