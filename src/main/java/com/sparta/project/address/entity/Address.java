package com.sparta.project.address.entity;

import com.sparta.project.address.dto.request.ReqCreateAddressDto;
import com.sparta.project.address.dto.request.ReqUpdateAddressDto;
import com.sparta.project.global.domain.BaseEntity;
import com.sparta.project.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "address_name", nullable = false, length = 100)
  private String addressName;

  @Column(name = "postal_code", nullable = false, length = 5)
  private String postalCode;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "detail_address", nullable = false)
  private String detailAddress;

  @Column(name = "delivery_request")
  private String deliveryRequest;

  @Column(name = "entrance_password")
  private String entrancePassword;

  @Column(name = "is_default")
  private boolean isDefault;

  private Address(
      User user,
      String addressName,
      String postalCode,
      String address,
      String detailAddress,
      String deliveryRequest,
      String entrancePassword,
      boolean isDefault
  ) {
    this.user = user;
    this.addressName = addressName;
    this.postalCode = postalCode;
    this.address = address;
    this.detailAddress = detailAddress;
    this.deliveryRequest = deliveryRequest;
    this.entrancePassword = entrancePassword;
    this.isDefault = isDefault;
  }

  public static Address create(
      User user,
      ReqCreateAddressDto dto,
      boolean isDefault
  ) {
    return new Address(
        user,
        dto.getAddressName(),
        dto.getPostalCode(),
        dto.getAddress(),
        dto.getDetailAddress(),
        dto.getDeliveryRequest(),
        dto.getEntrancePassword(),
        isDefault
    );
  }

  public void updateAddress(
      ReqUpdateAddressDto reqDto
  ) {

    if (reqDto.getAddressName() != null) {
      this.addressName = reqDto.getAddressName();
    }

    if (reqDto.getPostalCode() != null) {
      this.postalCode = reqDto.getPostalCode();
    }

    if (reqDto.getAddress() != null) {
      this.address = reqDto.getAddress();
    }

    if (reqDto.getDetailAddress() != null) {
      this.detailAddress = reqDto.getDetailAddress();
    }

    if (reqDto.getDeliveryRequest() != null) {
      this.deliveryRequest = reqDto.getDeliveryRequest();
    }

    if (reqDto.getEntrancePassword() != null) {
      this.entrancePassword = reqDto.getEntrancePassword();
    }

  }

  public void setDefaultAddress() {
    this.isDefault = true;
  }
}
