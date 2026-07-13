package com.sparta.project.cart.domain.domain.entity;

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




  private void validatePositiveQuantity(int quantity) {
    if (quantity < 1) {
      throw new IllegalArgumentException("장바구니 상품 수량은 1개 이상이어야 합니다.");
    }
  }




}