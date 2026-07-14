package com.sparta.project.address.presentation.dto.response;

import com.sparta.project.address.domain.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResAddressDto {

  private UUID id;
  private String addressName;
  private String postalCode;
  private String address;
  private String detailAddress;
  private String deliveryRequest;
  private String entrancePassword;
  private boolean isDefault;


  public ResAddressDto(Address address) {
    this.id = address.getId();
    this.addressName = address.getAddressName();
    this.postalCode = address.getPostalCode();
    this.address = address.getAddress();
    this.detailAddress = address.getDetailAddress();
    this.deliveryRequest = address.getDeliveryRequest();
    this.entrancePassword = address.getEntrancePassword();
    this.isDefault = address.isDefault();
  }
}
