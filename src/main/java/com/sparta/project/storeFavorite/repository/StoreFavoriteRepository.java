package com.sparta.project.storeFavorite.repository;

import com.sparta.project.storeFavorite.entity.StoreFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreFavoriteRepository extends JpaRepository<StoreFavorite, UUID> {

  Optional<StoreFavorite> findByUser_IdAndStore_Id(Long userId, UUID storeId);

  List<StoreFavorite> findAllByUser_Id(Long userId);

  boolean existsByUser_IdAndStore_Id(Long userId, UUID storeId);
}
