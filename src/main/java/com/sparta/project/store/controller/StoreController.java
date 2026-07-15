package com.sparta.project.store.controller;

import com.sparta.project.store.dto.StoreCreateRequestDto;
import com.sparta.project.store.dto.StoreResponseDto;
import com.sparta.project.store.dto.StoreUpdateRequestDto;
import com.sparta.project.store.service.StoreService;
import jakarta.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 가맹점 등록 - OWNER
    @PostMapping
    public ResponseEntity<StoreResponseDto> createStore(
            @Valid
            @RequestBody
            StoreCreateRequestDto request, Principal principal) {
        StoreResponseDto response = storeService.createStore(request, principal.getName());
        return ResponseEntity.created(URI.create("/api/stores/" + response.getId())).body(response);
    }

    // 가맹점 목록 조회 - 전체
    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> getStores() {
        return ResponseEntity.ok(storeService.getStores());
    }

    // 내가 등록한 가맹점 조회 - OWNER ({storeId} 매핑보다 먼저 선언되어야 한다)
    @GetMapping("/my")
    public ResponseEntity<List<StoreResponseDto>> getMyStores(Principal principal) {
        return ResponseEntity.ok(storeService.getMyStores(principal.getName()));
    }

    // 가맹점 상세 조회 - 전체
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> getStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.getStore(storeId));
    }

    // 가맹점 정보 수정 - OWNER
    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> updateStore(
            @PathVariable Long storeId,
            @Valid @RequestBody StoreUpdateRequestDto request,
            Principal principal) {
        return ResponseEntity.ok(storeService.updateStore(storeId, request, principal.getName()));
    }

    // 가맹점 삭제 - OWNER
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId, Principal principal) {
        storeService.deleteStore(storeId, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
