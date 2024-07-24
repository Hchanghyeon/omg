package com.chang.omg.domain.member.exception;

import com.chang.omg.global.exception.BusinessException;
import com.chang.omg.global.exception.ExceptionCode;

import lombok.Getter;

@Getter
public class MemberException extends BusinessException {

    public MemberException(final ExceptionCode exceptionCode, final String rejectedValues) {
        super(exceptionCode, rejectedValues);
    }
}
