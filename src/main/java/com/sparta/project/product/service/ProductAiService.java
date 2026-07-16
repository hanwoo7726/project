package com.sparta.project.product.service;


import com.sparta.project.product.ai.GeminiClient;
import com.sparta.project.product.ai.entity.AiLog;
import com.sparta.project.product.ai.repository.AiLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

// AI 상품 설명 생성 및 로그 기록
@Service
@RequiredArgsConstructor
public class ProductAiService {

    private static final String LENGTH_LIMIT = "50자 내외로 작성해주세요.";

    private final GeminiClient geminiClient;
    private final AiLogRepository aiLogRepository;

    // 상품 설명 생성, 실패시 null 반환
    @Transactional
    public String generateDescription(UUID productId, String userPrompt, String createBy){
        String finalPrompt = userPrompt + LENGTH_LIMIT;

        try{
            String response = geminiClient.generate(finalPrompt);

            // 50자 넘으면 자르기
            if(response != null && response.length() > 50){
                response = response.substring(0, 50);
            }

            aiLogRepository.save(
                    AiLog.success(productId, finalPrompt, response, createBy)
            );
            return response;
        }
        catch (Exception e){
            // 호출 실패해도 상품 등록 이어서, 로그만 남김
            aiLogRepository.save(
                    AiLog.fail(productId, finalPrompt, createBy)
            );
            return null;
        }
    }
}
