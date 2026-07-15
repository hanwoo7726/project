package com.sparta.project.global.infrastructure.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

  private final int code;
  private final String message;
  private final T data;

  public static <T> ApiResponse<T> success(HttpStatus status, T data) {
    return new ApiResponse<>(
        status.value(),
        "성공",
        data
    );
  }

  public static ApiResponse<Void> success(HttpStatus status) {
    return new ApiResponse<>(
        status.value(),
        "성공",
        null
    );
  }


  public static ApiResponse<Void> error(HttpStatus status, String message) {
    return new ApiResponse<>(
        status.value(),
        message,
        null
    );
  }

  public static <T>ApiResponse<T> error(HttpStatus status, T data) {
    return new ApiResponse<>(
        status.value(),
        "실패",
        data
    );
  }
}
