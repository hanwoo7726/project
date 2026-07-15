package com.sparta.project.store.repository;

import com.sparta.project.store.entity.Store;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 소프트 삭제(deletedAt) 되지 않은 가맹점만 조회한다.
    List<Store> findAllByDeletedAtIsNull();

    Optional<Store> findByIdAndDeletedAtIsNull(Long id);

    List<Store> findAllByOwnerUsernameAndDeletedAtIsNull(String ownerUsername);
}
