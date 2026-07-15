package com.sparta.project.product.infrastructure.ai;

import com.sparta.project.product.infrastructure.ai.dto.GeminiRequest;
import com.sparta.project.product.infrastructure.ai.dto.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GeminiClient {

    private final RestClient restClient;
    private final String apiKey;
    private final String apiUrl;

    public GeminiClient(
            @Value("${gemini.api.key}") String apiKey,
            @Value("${gemini.api.url}") String apiUrl
            ) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.restClient = RestClient.create();
    }

    // 프롬프트 보내고 텍스트 반환
    public String generate(String prompt){
        GeminiResponse response = restClient.post()
                .uri(apiUrl)
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .body(GeminiRequest.of(prompt))
                .retrieve()
                .body(GeminiResponse.class);

        return response == null ? null : response.extractText();
    }

    public String getContents(String prompt) {
        return generate(prompt);
    }
}
