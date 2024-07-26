package com.chang.omg.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.chang.omg.global.exception.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {

    AUTH_EXPIRED_REGISTER_TOKEN(HttpStatus.BAD_REQUEST, "AUT-001", "RegisterToken 토큰 만료"),
    AUTH_INVALID_TOKEN(HttpStatus.BAD_REQUEST, "AUT-002", "유효하지 않은 Token"),
    AUTH_FAIL_TO_VALIDATE_TOKEN(HttpStatus.BAD_REQUEST, "AUT-003", "토큰을 검증할 수 없음");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
