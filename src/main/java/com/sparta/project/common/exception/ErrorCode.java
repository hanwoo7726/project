package com.sparta.project.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // ----- Store -----
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 가맹점입니다."),
    STORE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 가맹점에 대한 권한이 없습니다."),
    STORE_CONFLICT(HttpStatus.CONFLICT,
            "다른 사용자가 먼저 수정했습니다. 최신 정보를 다시 조회한 뒤 재시도해주세요.");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }
}
