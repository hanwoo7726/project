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
    public ReviewResponseDto createReview(Integer rating, String content, String imageUrl, UUID storeId, String orderId) {
        // 1. 손님이 입력한 데이터 담기
        Review review = new Review(rating, content, imageUrl, storeId, orderId);

        // 2. 레포지토리를 통해 데이터베이스에 저장
        Review savedReview = reviewRepository.save(review);

        // 3. ⭐️ 엔티티를 DTO로 래핑하여 반환
        return new ReviewResponseDto(savedReview);
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> getReviewsByStore(UUID storeId, Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findByReviewStoreId(storeId, pageable);

        // 4. ⭐️ Page 내부의 Review 엔티티들을 map 메서드를 통해 ReviewResponseDto로 일괄 변환
        return reviewPage.map(ReviewResponseDto::new);
    }
}