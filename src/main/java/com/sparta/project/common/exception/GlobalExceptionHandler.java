package com.sparta.project.common.exception;

import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(
            BusinessException e, HttpServletRequest request) {
        HttpStatus status = e.getErrorCode().getStatus();
        ErrorResponse body = new ErrorResponse(
                status.value(), status.getReasonPhrase(),
                e.getMessage(), request.getRequestURI(), null);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        List<ErrorResponse.FieldErrorDetail> details = e.getBindingResult()
                .getFieldErrors().stream()
                .map(fe -> new ErrorResponse.FieldErrorDetail(
                        fe.getField(), fe.getDefaultMessage()))
                .toList();
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "Bad Request",
                "입력값 검증에 실패했습니다.", request.getRequestURI(), details);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({
            ObjectOptimisticLockingFailureException.class,
            OptimisticLockException.class
    })
    public ResponseEntity<ErrorResponse> handleOptimisticLock(
            Exception e, HttpServletRequest request) {
        ErrorCode code = ErrorCode.STORE_CONFLICT;
        HttpStatus status = code.getStatus();
        ErrorResponse body = new ErrorResponse(
                status.value(), status.getReasonPhrase(),
                code.getDefaultMessage(), request.getRequestURI(), null);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
            Exception e, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                "서버 오류가 발생했습니다.", request.getRequestURI(), null);
        return ResponseEntity.internalServerError().body(body);
    }
}
