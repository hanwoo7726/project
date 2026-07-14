package com.sparta.project.global.infrastructure.presentation.advice;

import com.sparta.project.global.exception.CustomException;
import com.sparta.project.global.infrastructure.presentation.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {

    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(ApiResponse.error(
            e.getErrorCode().getStatus(),
            e.getErrorCode().getMessage()
        ));
  }
}
