package com.sparta.project.product.infrastructure.ai.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_ai_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiLog {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "ai_log_id")
    private UUID aiLogId;

    @Column(name = "product_id")
    private UUID productId; // 연결된 상품

    @Column(name = "request_prompt", columnDefinition = "TEXT", nullable = false)
    private String requestPrompt; // 전송한 최종 프롬프트

    @Column(name = "response_text", columnDefinition = "TEXT")
    private String responseContent; // AI 응답 원문

    @Column(nullable = false, length = 20)
    private String status;  // Success / Fail

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 100)
    private String createBy;    // 요청자

    // 성공 로그 생성
    public static AiLog success(UUID productId, String prompt, String response, String createBy) {
        AiLog log = new AiLog();
        log.productId = productId;
        log.requestPrompt = prompt;
        log.responseContent = response;
        log.status = "SUCCESS";
        log.createdAt = LocalDateTime.now();
        log.createBy = createBy;
        return log;
    }

    // 실패 로그 생성
    public static AiLog fail(UUID productId, String prompt, String createBy) {
        AiLog log = new AiLog();
        log.productId = productId;
        log.requestPrompt = prompt;
        log.responseContent = null;
        log.status = "FAIL";
        log.createdAt = LocalDateTime.now();
        log.createBy = createBy;
        return log;
    }
}
