package com.chang.omg.global.exception;

import static com.chang.omg.global.exception.GlobalExceptionCode.*;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final ApiException apiException) {
        log.error("외부 API 요청 에러: {}", apiException.getMessage(), apiException);
        log.error(Arrays.toString(apiException.getRejectedValues()));

        return ResponseEntity.status(apiException.getExceptionCode().getStatus())
                .body(new ErrorResponse(
                                apiException.getExceptionCode().getCode(),
                                apiException.getExceptionCode().getMessage()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        log.error("{}", GLOBAL_INTERNAL_SERVER_ERROR, exception);

        return ResponseEntity.status(GLOBAL_INTERNAL_SERVER_ERROR.getStatus())
                .body(new ErrorResponse(
                                GLOBAL_INTERNAL_SERVER_ERROR.getCode(),
                                GLOBAL_INTERNAL_SERVER_ERROR.getMessage()
                        )
                );
    }
}
