package com.sparta.project.address.domain.repository;

import com.sparta.project.address.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

  boolean existsByUser_IdAndIsDefaultIsTrueAndDeletedAtIsNull(Long userId);

  @Modifying
  @Query("""
      update Address a
      set a.isDefault = false
      where a.user.id = :userId
      and a.id <> :addressId
      and a.isDefault = true
      and a.deletedAt is null""")
  void unsetDefaultAddressesByIdAndUserIdAndDeletedAtIsNull(
      @Param("addressId") UUID addressId,
      @Param("userId") Long userId
      );

  Optional<Address> findByIdAndUser_IdAndDeletedAtIsNull(UUID addressId, Long userId);

  List<Address> findAllByUser_IdAndDeletedAtIsNull(Long userId);

  Optional<Address> findFirstByUser_IdAndIdNotAndDeletedAtIsNullOrderByCreatedAtDesc(Long userId, UUID addressId);
}
