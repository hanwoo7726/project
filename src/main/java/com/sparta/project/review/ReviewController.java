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

        // ⭐️ 서비스가 이제 ReviewResponseDto를 반환합니다.
        ReviewResponseDto createdReviewDto = reviewService.createReview(
                requestDto.getReviewRating(),
                requestDto.getReviewContent(),
                requestDto.getReviewImageUrl(),
                requestDto.getReviewStoreId(),
                requestDto.getReviewOrderId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("code", 201);
        response.put("message", "리뷰가 등록되었습니다.");
        response.put("data", createdReviewDto); // ⭐️ 엔티티 대신 DTO를 결과 데이터에 세팅

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getReviewsByStore(
            @RequestParam UUID storeId,
            Pageable pageable) {

        // ⭐️ 서비스가 이제 Page<ReviewResponseDto>를 반환합니다.
        Page<ReviewResponseDto> reviewDtoPage = reviewService.getReviewsByStore(storeId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "리뷰 목록 조회가 완료되었습니다.");
        response.put("data", reviewDtoPage.getContent()); // ⭐️ DTO 리스트가 담겨 프론트로 전송됨

        return ResponseEntity.ok(response);
    }

}