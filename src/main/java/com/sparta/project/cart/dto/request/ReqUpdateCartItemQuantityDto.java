package com.sparta.project.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReqUpdateCartItemQuantityDto {
  @NotNull
  @Min(value = 1)
  private Integer quantity;
}
