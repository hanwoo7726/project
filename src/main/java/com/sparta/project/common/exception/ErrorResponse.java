package com.sparta.project.common.exception;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

/**
 * 모든 예외가 동일한 JSON 구조로 나가도록 하는 공통 에러 응답 형식.
 *
 * <pre>
 * {
 *   "timestamp": "2026-07-13T14:20:30",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "존재하지 않는 가맹점입니다. id=999",
 *   "path": "/api/stores/999",
 *   "fieldErrors": null
 * }
 * </pre>
 */
@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;          // HTTP 상태 코드 (예: 404)
    private final String error;        // 상태 문구 (예: "Not Found")
    private final String message;      // 사람이 읽을 메시지
    private final String path;         // 요청 경로
    private final List<FieldErrorDetail> fieldErrors; // 검증 실패 상세 (없으면 null)

    public ErrorResponse(int status, String error, String message, String path,
            List<FieldErrorDetail> fieldErrors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }

    @Getter
    public static class FieldErrorDetail {
        private final String field;
        private final String reason;

        public FieldErrorDetail(String field, String reason) {
            this.field = field;
            this.reason = reason;
        }
    }
}
