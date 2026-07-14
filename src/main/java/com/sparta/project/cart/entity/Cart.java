package com.sparta.project.cart.entity;

import com.sparta.project.global.exception.CustomException;
import com.sparta.project.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItem> cartItems = new ArrayList<>();

  private Cart(User user) {
    this.user = user;
  }

  public static Cart create(User user) {
    return new Cart(user);
  }


  public boolean containsDifferentStore(Store store) {
    return hasItems() && !isSameStore(store);
  }


  public void clearCart() {
    this.cartItems.clear();
    this.store = null;
  }

  public void addItem(Product product, Integer quantity) {

    Store productStore = product.getStore();

    if (this.cartItems.isEmpty()) {
      changeStore(productStore);
    }

    if (!isSameStore(productStore)) {
      throw new CustomException(ErrorCode.CART_STORE_CONFLICT);
    }

    CartItem findItem = this.cartItems.stream()
        .filter(item -> item.getProduct().getId().equals(product.getId()))
        .findFirst()
        .orElse(null);

    if (findItem != null) {
      findItem.increaseQuantity(quantity);
    } else {
      CartItem cartItem = CartItem.create(this, product, quantity);
      this.cartItems.add(cartItem);
    }
  }

  public int totalPrice() {
    return cartItems.stream()
        .mapToInt(CartItem::cartItemPrice)
        .sum();
  }


  public CartItem findItem(UUID cartItemId) {
    return this.cartItems.stream()
        .filter(cartItem -> cartItem.getId().equals(cartItemId))
        .findFirst()
        .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));
  }


  public void removeItem(CartItem findCartItem) {
    this.cartItems.remove(findCartItem);
    findCartItem.disconnectCart();

    if (cartItems.isEmpty()) {
      store = null;
    }
  }


  private boolean isSameStore(Store store) {
    return this.store != null && this.store.getId().equals(store.getId());
  }

  private boolean hasItems() {
    return !cartItems.isEmpty();
  }

  private void changeStore(Store store) {
    this.store = store;
  }



}