package com.sparta.project.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;


    @Transactional
    public Review createReview(Integer rating, String content, String imageUrl, UUID storeId, String orderId) {
        // 1. 손님이 입력한 데이터 담기
        Review review = new Review(rating, content, imageUrl, storeId, orderId);

        // 2. 레포지토리를 통해 데이터베이스에 저장
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Page<Review> getReviewsByStore(UUID storeId, Pageable pageable) {

        return reviewRepository.findByReviewStoreId(storeId, pageable);
    }
}