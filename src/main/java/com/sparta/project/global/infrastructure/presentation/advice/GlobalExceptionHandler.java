package com.sparta.project.global.infrastructure.presentation.advice;

import com.sparta.project.global.exception.CustomException;
import com.sparta.project.global.infrastructure.presentation.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handlerValidationException(MethodArgumentNotValidException e) {

    Map<String, String> errors = new LinkedHashMap<>();

    e.getBindingResult()
        .getFieldErrors()
        .forEach(fieldError ->
            errors.putIfAbsent(
                fieldError.getField(),
                fieldError.getDefaultMessage()
            )
        );

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(HttpStatus.BAD_REQUEST, errors));
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<Void>> handlerCustomException(CustomException e) {

    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(ApiResponse.error(
            e.getErrorCode().getStatus(),
            e.getErrorCode().getMessage()
        ));
  }

}
