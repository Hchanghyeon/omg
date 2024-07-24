package com.chang.omg.global.exception;

import static com.chang.omg.global.exception.GlobalExceptionCode.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chang.omg.domain.member.exception.MemberException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e) {
        final List<FieldError> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(FieldError.class::cast)
                .toList();

        log.warn("사용자 Input 값 예외 발생: {}", GLOBAL_BAD_REQUEST.getMessage(), e);

        errors.forEach(
                error -> log.warn("{}, field: {}, rejectedValue: {}",
                        error.getDefaultMessage(),
                        error.getField(),
                        error.getRejectedValue()
                )
        );

        return ResponseEntity.status(GLOBAL_BAD_REQUEST.getStatus())
                .body(new ErrorResponse(
                                GLOBAL_BAD_REQUEST.getCode(),
                                GLOBAL_BAD_REQUEST.getMessage()
                        )
                );
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(final MemberException memberException) {
        log.error("Member 관련 예외 발생: {}", memberException.getMessage(), memberException);
        log.error(Arrays.toString(memberException.getRejectedValues()));

        return ResponseEntity.status(memberException.getExceptionCode().getStatus())
                .body(new ErrorResponse(
                                memberException.getExceptionCode().getCode(),
                                memberException.getExceptionCode().getMessage()
                        )
                );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final ApiException apiException) {
        log.error("외부 API 요청 예외 발생: {}", apiException.getMessage(), apiException);
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
