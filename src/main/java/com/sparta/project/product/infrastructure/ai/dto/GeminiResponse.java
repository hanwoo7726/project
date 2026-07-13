package com.sparta.project.product.infrastructure.ai.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// 제미나이 응답에서 텍스트만 추출
@JsonIgnoreProperties(ignoreUnknown = true)
public record GeminiResponse(List<Candidate> candidates) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Candidate(Content content) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Content(List<Part> parts) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Part(String text) {}

    // 생성된 텍스트 추출하기
    public String extractText(){
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        return candidates.get(0).content().parts().get(0).text();
    }
}
