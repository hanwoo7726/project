package com.sparta.project.domain.application.service;

import com.sparta.project.domain.domain.entity.StoreFavorite;
import com.sparta.project.domain.domain.repository.StoreFavoriteRepository;
import com.sparta.project.domain.presentation.dto.response.ResStoreFavoriteDto;
import com.sparta.project.domain.presentation.dto.response.ResStoreFavoriteStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreFavoriteService {

  private final StoreFavoriteRepository storeFavoriteRepository;
  private final UserRepository userRepository;
  private final StoreRepository storeRepository;

  public List<ResStoreFavoriteDto> getFavorites(Long userId) {

    return storeFavoriteRepository.findAllByUser_Id(userId).stream()
        .map(ResStoreFavoriteDto::from)
        .toList();
  }

  @Transactional
  public ResStoreFavoriteStatusDto addFavorite(Long userId, UUID storeId) {

    boolean existsFavorite = storeFavoriteRepository.existsByUser_IdAndStore_Id(userId, storeId);
    if (existsFavorite) {
      return ResStoreFavoriteStatusDto.favorited(userId, storeId);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("store not found"));

    StoreFavorite favorite = StoreFavorite.create(user, store);

    StoreFavorite storeFavorite = storeFavoriteRepository.save(favorite);

    return ResStoreFavoriteStatusDto.favorited(
        storeFavorite.getUser().getId(),
        storeFavorite.getStore().getId());
  }

  @Transactional
  public ResStoreFavoriteStatusDto removeFavorite(Long userId, UUID storeId) {

    StoreFavorite storeFavorite = storeFavoriteRepository.findByUser_IdAndStore_Id(userId, storeId)
        .orElseThrow(() -> new RuntimeException("store_favorite not found"));

    storeFavoriteRepository.delete(storeFavorite);

    return ResStoreFavoriteStatusDto.removed(
        storeFavorite.getUser().getId(),
        storeFavorite.getStore().getId());
  }
}
