package com.sparta.project.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "p_review")
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_id")
    private UUID reviewId;

    @Column(name = "review_rating", nullable = false)
    private Integer reviewRating; // 만족도 점수 (1~5점)

    @Column(name = "review_content", length = 1000, nullable = false)
    private String reviewContent; // 리뷰 텍스트

    @Column(name = "review_image_url", length = 255)
    private String reviewImageUrl; // 포토 리뷰용 이미지 경로 (선택 가능하므로 nullable 빼기)

    @Column(name = "review_store_id", nullable = false)
    private UUID reviewStoreId; // 리뷰가 작성된 가게 ID

    @Column(name = "review_order_id", length = 50, nullable = false)
    private String reviewOrderId; // 어떤 주문에 대한 리뷰인지 (FK)

    // 리뷰 생성을 위한 생성자
    public Review(Integer reviewRating, String reviewContent, String reviewImageUrl, UUID reviewStoreId, String reviewOrderId) {
        this.reviewRating = reviewRating;
        this.reviewContent = reviewContent;
        this.reviewImageUrl = reviewImageUrl;
        this.reviewStoreId = reviewStoreId;
        this.reviewOrderId = reviewOrderId;
    }
}