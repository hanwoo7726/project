package com.sparta.project.address.application.service;

import com.sparta.project.address.domain.entity.Address;
import com.sparta.project.address.domain.repository.AddressRepository;
import com.sparta.project.address.presentation.dto.request.ReqCreateAddressDto;
import com.sparta.project.address.presentation.dto.request.ReqUpdateAddressDto;
import com.sparta.project.address.presentation.dto.response.ResAddressDto;
import com.sparta.project.global.exception.CustomException;
import com.sparta.project.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

  private final AddressRepository addressRepository;
  private final UserRepository userRepository;

  /*생성*/
  @Transactional
  public ResAddressDto createAddress(ReqCreateAddressDto reqDto, Long userId) {
    User findUser = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    boolean hasAddress = addressRepository.existsByUser_IdAndIsDefaultIsTrueAndDeletedAtIsNull(userId);
    boolean isDefault = !hasAddress;

    Address address = Address.create(findUser, reqDto, isDefault);

    Address saveAddress = addressRepository.save(address);

    return new ResAddressDto(saveAddress);
  }

  /*단건 조회*/
  public ResAddressDto getAddress(UUID addressId, Long userId) {

    Address findAddress = addressRepository.findByIdAndUser_IdAndDeletedAtIsNull(addressId, userId)
        .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));

    return new ResAddressDto(findAddress);
  }

  public List<ResAddressDto> getAddresses(Long userId) {

    return addressRepository.findAllByUser_IdAndDeletedAtIsNull(userId)
        .stream()
        .map(ResAddressDto::new)
        .toList();
  }

  @Transactional
  public ResAddressDto updateAddress(UUID addressId, ReqUpdateAddressDto reqDto, Long userId) {

    Address findAddress = addressRepository.findByIdAndUser_IdAndDeletedAtIsNull(addressId, userId)
        .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));

    findAddress.updateAddress(reqDto);

    return new ResAddressDto(findAddress);
  }

  @Transactional
  public ResAddressDto updateDefaultAddress(UUID addressId, Long userId) {

    Address findAddress = addressRepository.findByIdAndUser_IdAndDeletedAtIsNull(addressId, userId)
        .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));

    addressRepository.unsetDefaultAddressesByIdAndUserIdAndDeletedAtIsNull(addressId, userId);

    findAddress.setDefaultAddress();

    return new ResAddressDto(findAddress);
  }

  @Transactional
  public ResAddressDto deleteAddress(UUID addressId, Long userId) {
    Address findAddress = addressRepository.findByIdAndUser_IdAndDeletedAtIsNull(addressId, userId)
        .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));

    boolean isDefault = findAddress.isDefault();

    if (isDefault) {
      addressRepository.findFirstByUser_IdAndIdNotAndDeletedAtIsNullOrderByCreatedAtDesc(userId, addressId)
          .ifPresent(Address::setDefaultAddress);
    }

    findAddress.softDelete(userId);

    return new ResAddressDto(findAddress);
  }
}
