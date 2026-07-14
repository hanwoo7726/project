package com.sparta.project.cart.domain.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqAddCartItemDto {

  @NotNull
  private UUID productId;

  @NotNull
  @Min(value = 1)
  private Integer quantity;

  private boolean replaceCart = false;
}
