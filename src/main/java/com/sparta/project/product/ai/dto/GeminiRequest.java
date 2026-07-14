package com.sparta.project.product.ai.dto;

import java.util.List;

public record GeminiRequest(List<Content> contents) {

    public record Content(List<Part> parts ){}
    public record Part(String text){}

    // 프롬프트 문자열 하나로 요청 객체 생성
    public static GeminiRequest of(String prompt){
        return new GeminiRequest(
                List.of(new Content(List.of(new Part(prompt))))
        );

    }
}
