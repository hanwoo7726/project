package com.sparta.project.review;

import lombok.Getter;
import java.util.UUID;

@Getter
public class ReviewResponseDto {
    private final UUID reviewId;
    private final Integer reviewRating;
    private final String reviewContent;
    private final String reviewImageUrl;
    private final UUID reviewStoreId;
    private final String reviewOrderId;

    // ⭐️ 실제 Review 엔티티의 getter 메서드명들과 100% 일치하도록 매핑했습니다.
    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.reviewRating = review.getReviewRating();
        this.reviewContent = review.getReviewContent();
        this.reviewImageUrl = review.getReviewImageUrl();
        this.reviewStoreId = review.getReviewStoreId();
        this.reviewOrderId = review.getReviewOrderId();
    }
}