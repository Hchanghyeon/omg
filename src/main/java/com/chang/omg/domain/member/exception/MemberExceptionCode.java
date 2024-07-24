package com.chang.omg.domain.member.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.chang.omg.global.exception.ExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionCode implements ExceptionCode {
    MEMBER_NOT_FOUND(NOT_FOUND, "MEM-001", "사용자를 찾을 수 없습니다."),
    MEMBER_CREATE_FAIL(BAD_REQUEST, "MEM-002", "사용자의 입력 값이 잘못되었습니다."),
    MEMBER_ALREADY_EXISTS(BAD_REQUEST, "MEM-003", "가입된 사용자가 이미 존재합니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
