package com.sparta.project.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    // 특정 가게(storeId)의 리뷰 목록을 페이징 처리해서 가져오는 메서드
    Page<Review> findByReviewStoreId(UUID storeId, Pageable pageable);
}