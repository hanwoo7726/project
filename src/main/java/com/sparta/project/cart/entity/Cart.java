package com.sparta.project.cart.entity;

import com.sparta.project.global.exception.CustomException;
import com.sparta.project.global.exception.ErrorCode;
import com.sparta.project.product.entity.Product;
import com.sparta.project.store.entity.Store;
import com.sparta.project.user.entity.User;
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


  public boolean containsDifferentStore(Long storeId) {
    return hasItems() && !isSameStore(storeId);
  }


  public void clearCart() {
    this.cartItems.clear();
    this.store = null;
  }

  public void addItem(Product product, Store store, Integer quantity) {

    if (this.cartItems.isEmpty()) {
      changeStore(store);
    }

    if (!isSameStore(store.getId())) {
      throw new CustomException(ErrorCode.CART_STORE_CONFLICT);
    }

    CartItem findItem = this.cartItems.stream()
        .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
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


  private boolean isSameStore(Long storeId) {
    return this.store != null && this.store.getId().equals(storeId);
  }

  private boolean hasItems() {
    return !cartItems.isEmpty();
  }

  private void changeStore(Store store) {
    this.store = store;
  }



}