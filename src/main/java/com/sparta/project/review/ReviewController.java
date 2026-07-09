package com.sparta.project.review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Getter
    public static class ReviewRequestDto {
        private Integer reviewRating;
        private String reviewContent;
        private String reviewImageUrl;
        private UUID reviewStoreId;
        private String reviewOrderId;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createReview(@RequestBody ReviewRequestDto requestDto) {

        Review createdReview = reviewService.createReview(
                requestDto.getReviewRating(),
                requestDto.getReviewContent(),
                requestDto.getReviewImageUrl(),
                requestDto.getReviewStoreId(),
                requestDto.getReviewOrderId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("code", 201);
        response.put("message", "리뷰가 등록되었습니다.");
        response.put("data", createdReview);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getReviewsByStore(
            @RequestParam UUID storeId,
            Pageable pageable) {

        Page<Review> reviewPage = reviewService.getReviewsByStore(storeId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "리뷰 목록 조회가 완료되었습니다.");
        response.put("data", reviewPage.getContent());

        return ResponseEntity.ok(response);
    }

}