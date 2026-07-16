package com.sparta.project.cart.service;


import com.sparta.project.cart.entity.Cart;
import com.sparta.project.cart.entity.CartItem;
import com.sparta.project.cart.repository.CartRepository;
import com.sparta.project.cart.dto.request.ReqAddCartItemDto;
import com.sparta.project.cart.dto.response.ResCartDto;
import com.sparta.project.cart.dto.response.ResUpdateCartItemQuantityDto;
import com.sparta.project.global.exception.CustomException;
import com.sparta.project.global.exception.ErrorCode;

import com.sparta.project.product.entity.Product;
import com.sparta.project.product.repository.ProductRepository;
import com.sparta.project.store.entity.Store;
import com.sparta.project.store.repository.StoreRepository;
import com.sparta.project.user.entity.User;
import com.sparta.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

  private final CartRepository cartRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final StoreRepository storeRepository;


  @Transactional
  public ResCartDto addCartItem(Long userId, ReqAddCartItemDto reqDto) {

    Cart findCart = cartRepository.findByUser_Id(userId)
        .orElseGet(() -> {
          User findUser = userRepository.findById(userId)
              .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

          return Cart.create(findUser);
        });

    Product findProduct = productRepository.findById(reqDto.getProductId())
        .orElseThrow(() -> new RuntimeException("Product not found"));

    Store findStore = storeRepository.findById(findProduct.getStoreId())
        .orElseThrow(() -> new RuntimeException("Store not found"));

    boolean differentStore = findCart.containsDifferentStore(findProduct.getStoreId());

    if (differentStore) {
      if (!reqDto.isReplaceCart()) {
        throw new CustomException(ErrorCode.CART_STORE_CONFLICT);
      }

      findCart.clearCart();
    }

    findCart.addItem(findProduct, findStore, reqDto.getQuantity());
    cartRepository.save(findCart);

    return ResCartDto.from(findCart);
  }


  public ResCartDto getCart(Long userId) {
    return cartRepository.findByUser_Id(userId)
        .map(ResCartDto::from)
        .orElseGet(ResCartDto::empty);
  }



  @Transactional
  public ResUpdateCartItemQuantityDto updateCartItemQuantity(Long userId, UUID cartItemId, Integer quantity) {
    Cart findCart = cartRepository.findByUser_Id(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));


    CartItem findCartItem = findCart.findItem(cartItemId);

    findCartItem.updateQuantity(quantity);

    return ResUpdateCartItemQuantityDto.from(findCartItem);
  }

  @Transactional
  public void deleteCartItem(Long userId, UUID cartItemId) {
    Cart findCart = cartRepository.findByUser_Id(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

    CartItem findCartItem = findCart.findItem(cartItemId);

    findCart.removeItem(findCartItem);
  }

  @Transactional
  public void clearCart(Long userId) {
    Cart findCart = cartRepository.findByUser_Id(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

    findCart.clearCart();
  }
}


