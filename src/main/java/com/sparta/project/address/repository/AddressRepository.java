package com.sparta.project.address.repository;

import com.sparta.project.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

  boolean existsByUser_IdAndIsDefaultIsTrueAndDeletedAtIsNull(Long userId);

  Optional<Address> findByIdAndUser_IdAndDeletedAtIsNull(UUID addressId, Long userId);

  List<Address> findAllByUser_IdAndDeletedAtIsNull(Long userId);

  Optional<Address> findFirstByUser_IdAndIdNotAndDeletedAtIsNullOrderByCreatedAtDesc(Long userId, UUID addressId);

  Optional<Address> findFirstByUser_IdAndIsDefaultTrueAndDeletedAtIsNull(Long userId);
}
