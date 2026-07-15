package com.sparta.project.review;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_review_ai_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewAiLog {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "review_ai_log_id")
    private UUID reviewAiLogId;

    @Column(name = "request_prompt", columnDefinition = "TEXT", nullable = false)
    private String requestPrompt; // AI에게 보낸 프롬프트

    @Column(name = "response_text", columnDefinition = "TEXT")
    private String responseContent; // AI가 생성해준 리뷰 답변

    @Column(nullable = false, length = 20)
    private String status;  // SUCCESS / FAIL

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 100)
    private String createBy;    // 요청한 유저 정보

    // 성공 로그 생성
    public static ReviewAiLog success(String prompt, String response, String createBy) {
        ReviewAiLog log = new ReviewAiLog();
        log.requestPrompt = prompt;
        log.responseContent = response;
        log.status = "SUCCESS";
        log.createdAt = LocalDateTime.now();
        log.createBy = createBy;
        return log;
    }

    // 실패 로그 생성
    public static ReviewAiLog fail(String prompt, String createBy) {
        ReviewAiLog log = new ReviewAiLog();
        log.requestPrompt = prompt;
        log.responseContent = null;
        log.status = "FAIL";
        log.createdAt = LocalDateTime.now();
        log.createBy = createBy;
        return log;
    }
}