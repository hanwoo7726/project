package com.sparta.project.store.service;

import com.sparta.project.store.dto.StoreCreateRequestDto;
import com.sparta.project.store.dto.StoreResponseDto;
import com.sparta.project.store.dto.StoreUpdateRequestDto;
import com.sparta.project.store.entity.Store;
import com.sparta.project.store.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public StoreResponseDto createStore(StoreCreateRequestDto request, String ownerUsername) {
        Store store = storeRepository.save(request.toEntity(ownerUsername));
        return StoreResponseDto.from(store);
    }

    public List<StoreResponseDto> getStores() {
        return storeRepository.findAllByDeletedAtIsNull().stream()
                .map(StoreResponseDto::from)
                .toList();
    }

    public StoreResponseDto getStore(Long storeId) {
        return StoreResponseDto.from(findActiveStore(storeId));
    }

    public List<StoreResponseDto> getMyStores(String ownerUsername) {
        return storeRepository.findAllByOwnerUsernameAndDeletedAtIsNull(ownerUsername).stream()
                .map(StoreResponseDto::from)
                .toList();
    }

    @Transactional
    public StoreResponseDto updateStore(Long storeId, StoreUpdateRequestDto request,
            String ownerUsername) {
        Store store = findActiveStore(storeId);
        validateOwner(store, ownerUsername);
        store.update(request.getName(), request.getCategory(), request.getAddress(),
                request.getPhone(), request.getOpenTime(), request.getCloseTime(),
                request.getMinimumOrderPrice(), request.getDeliveryFee());
        return StoreResponseDto.from(store);
    }

    @Transactional
    public void deleteStore(Long storeId, String ownerUsername) {
        Store store = findActiveStore(storeId);
        validateOwner(store, ownerUsername);
        store.softDelete();
    }

    private Store findActiveStore(Long storeId) {
        return storeRepository.findByIdAndDeletedAtIsNull(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가맹점입니다. id=" + storeId));
    }

    private void validateOwner(Store store, String ownerUsername) {
        if (!store.isOwnedBy(ownerUsername)) {
            throw new AccessDeniedException("해당 가맹점에 대한 권한이 없습니다.");
        }
    }
}
