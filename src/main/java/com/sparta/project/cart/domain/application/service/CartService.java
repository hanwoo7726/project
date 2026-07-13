package com.sparta.project.cart.domain.application.service;


import com.sparta.project.cart.domain.domain.entity.Cart;
import com.sparta.project.cart.domain.domain.repository.CartRepository;
import com.sparta.project.cart.domain.presentation.dto.request.ReqAddCartItemDto;
import com.sparta.project.cart.domain.presentation.dto.response.ResCartDto;
import com.sparta.project.global.exception.CustomException;
import com.sparta.project.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

  private final CartRepository cartRepository;


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

    boolean differentStore = findCart.containsDifferentStore(findProduct.getStore());

    if (differentStore) {
      if (!reqDto.isReplaceCart()) {
      }
      throw new CustomException(ErrorCode.CART_STORE_CONFLICT);

      findCart.clearCart();
    }

    findCart.addItem(findProduct, reqDto.getQuantity());
    cartRepository.save(findCart);

    return ResCartDto.from(findCart);
  }
}


