package com.sparta.project.common.exception;

import lombok.Getter;

/**
 * 우리 서비스의 "의미 있는" 예외.
 *
 * <p>{@link IllegalArgumentException} 같은 막연한 표준 예외 대신, 어떤 상태 코드로 응답할지를
 * 담은 {@link ErrorCode} 를 함께 들고 다닌다. 전역 핸들러({@code GlobalExceptionHandler})가
 * 이 예외를 받아 ErrorCode 의 상태 코드로 응답을 만든다.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    // 메시지에 id 등 상세 정보를 덧붙이고 싶을 때 사용
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
