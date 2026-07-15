package com.sparta.project.review;

import com.sparta.project.product.infrastructure.ai.GeminiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewAiService {

    private final GeminiClient geminiClient;
    private final ReviewAiLogRepository reviewAiLogRepository;

    /**
     * 가게 정보와 별점을 바탕으로 추천 리뷰를 생성합니다.
     */
    @Transactional
    public String suggestReviewText(String storeName, String menuName, Integer rating, String username) {
        // 1. AI 프롬프트 구성
        String prompt = String.format(
                "가게 이름은 '%s'이고, 주문한 메뉴는 '%s'입니다. " +
                        "사용자가 매긴 별점 만족도는 5점 만점에 %d점입니다. " +
                        "이 정보를 바탕으로 자연스럽고 생생한 맛집 리뷰를 150자 내외로 작성해주세요. " +
                        "문조는 정중하고 친근하게 작성하고, 불필요한 해시태그나 서론 없이 리뷰 본문 내용만 깔끔하게 답변해주세요.",
                storeName, menuName, rating
        );

        try {
            // 2. Gemini API 호출
            String aiResponse = geminiClient.getContents(prompt);

            // 3. 성공 로그 기록
            ReviewAiLog successLog = ReviewAiLog.success(prompt, aiResponse, username);
            reviewAiLogRepository.save(successLog);

            return aiResponse;

        } catch (Exception e) {
            log.error("AI 리뷰 생성 실패: ", e);

            // 4. 실패 로그 기록
            ReviewAiLog failLog = ReviewAiLog.fail(prompt, username);
            reviewAiLogRepository.save(failLog);

            throw new RuntimeException("AI 리뷰 추천에 실패했습니다. 다시 시도해주세요.");
        }
    }
}