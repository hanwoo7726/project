package com.sparta.project.address.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class ReqUpdateAddressDto {
  @NotBlank
  @Length(max = 100)
  private String addressName;

  @NotBlank
  @Pattern(regexp = "^\\d{5}$")
  private String postalCode;

  @NotBlank
  @Length(max = 255)
  private String address;

  @NotBlank
  @Length(max = 255)
  private String detailAddress;

  @Length(max = 255)
  private String deliveryRequest;

  @Length(max = 255)
  private String entrancePassword;

}
