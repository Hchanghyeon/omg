package com.chang.omg.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiExceptionCode implements ExceptionCode {
    API_INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "API-001", "게임사 서버에 에러가 발생하였습니다."),
    API_BAD_REQUEST(BAD_REQUEST, "API-002", "요청 정보가 잘못되었습니다."),
    API_FORBIDDEN(FORBIDDEN, "API-003", "요청 권한이 없경습니다."),
    API_TOO_MANY_REQUESTS(TOO_MANY_REQUESTS, "API_004", "요청이 너무 많습니다."),
    API_GAME_TYPE_IS_INVALID(BAD_REQUEST, "API_005", "게임 타입이 유효하지 않습니다."),
    API_UNKNOWN_ERROR(INTERNAL_SERVER_ERROR, "API-006", "알 수 없는 에러가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
