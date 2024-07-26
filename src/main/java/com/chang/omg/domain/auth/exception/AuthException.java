package com.chang.omg.domain.auth.exception;

import com.chang.omg.global.exception.BusinessException;
import com.chang.omg.global.exception.ExceptionCode;

import lombok.Getter;

@Getter
public class AuthException extends BusinessException {

    public AuthException(final ExceptionCode exceptionCode, final Object... rejectedValues) {
        super(exceptionCode, rejectedValues);
    }
}
