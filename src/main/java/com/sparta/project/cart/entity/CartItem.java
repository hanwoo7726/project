package com.sparta.project.cart.entity;

import com.sparta.project.global.exception.CustomException;
import com.sparta.project.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_cart_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "quantity")
  private int quantity;

  private CartItem(Cart cart, Product product, int quantity) {
    validatePositiveQuantity(quantity);

    this.cart = cart;
    this.product = product;
    this.quantity = quantity;
  }



  public static CartItem create(Cart cart, Product product, int quantity) {
    return new CartItem(cart, product, quantity);
  }

  public void increaseQuantity(int quantity) {
    validatePositiveQuantity(quantity);
    this.quantity += quantity;
  }

  public int cartItemPrice() {
    return this.product.getPrice() * quantity;
  }

  public void disconnectCart() {
    this.cart = null;
  }

  public void updateQuantity(Integer quantity) {
    validatePositiveQuantity(quantity);
    this.quantity = quantity;
  }

  private void validatePositiveQuantity(int quantity) {
    if (quantity < 1) {
      throw new CustomException(ErrorCode.QUANTITY_INVALID);
    }
  }



}