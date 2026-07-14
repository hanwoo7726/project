package com.sparta.project.address.controller;

import com.sparta.project.address.service.AddressService;
import com.sparta.project.address.dto.request.ReqCreateAddressDto;
import com.sparta.project.address.dto.request.ReqUpdateAddressDto;
import com.sparta.project.address.dto.response.ResAddressDto;
import com.sparta.project.global.infrastructure.presentation.response.ApiResponse;
import com.sparta.project.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

  private final AddressService addressService;

  // 주소 생성
  @PostMapping
  public ResponseEntity<ApiResponse<ResAddressDto>> createAddress(
      @RequestBody @Valid ReqCreateAddressDto reqDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    ResAddressDto resDto = addressService.createAddress(reqDto, userDetails.getUserId());

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success(
            HttpStatus.CREATED,
            resDto)
        );
  }

  // 주소 조회
  @GetMapping("/{addressId}")
  public ResponseEntity<ApiResponse<ResAddressDto>> getAddress(
      @PathVariable UUID addressId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
      ) {

    ResAddressDto resDto = addressService.getAddress(addressId, userDetails.getUserId());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(ApiResponse.success(
            HttpStatus.OK,
            resDto
        ));
  }

  // 주소 전체 조회
  @GetMapping
  public ResponseEntity<ApiResponse<List<ResAddressDto>>> getAddresses(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    List<ResAddressDto> addresses = addressService.getAddresses(userDetails.getUserId());

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(
            HttpStatus.OK,
            addresses
        ));
  }

  // 주소 수정
  @PatchMapping("/{addressId}")
  public ResponseEntity<ApiResponse<ResAddressDto>> updateAddress(
      @PathVariable UUID addressId,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody @Valid ReqUpdateAddressDto reqDto
  ) {

    ResAddressDto resDto = addressService.updateAddress(addressId, reqDto, userDetails.getUserId());

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(
            HttpStatus.OK,
            resDto
        ));
  }

  // 기본 주소로 변경
  @PatchMapping("/{addressId}/default")
  public ResponseEntity<ApiResponse<ResAddressDto>> updateDefaultAddress(
      @PathVariable UUID addressId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    ResAddressDto resDto = addressService.updateDefaultAddress(addressId, userDetails.getUserId());

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(
            HttpStatus.OK,
            resDto
        ));
  }

  // 주소 soft delete
  @DeleteMapping("/{addressId}")
  public ResponseEntity<ApiResponse<ResAddressDto>> deleteAddress(
      @PathVariable UUID addressId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    ResAddressDto resDto = addressService.deleteAddress(addressId, userDetails.getUserId());

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(
            HttpStatus.OK,
            resDto
        ));
  }
}
