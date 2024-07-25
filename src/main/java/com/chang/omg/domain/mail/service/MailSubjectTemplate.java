package com.chang.omg.domain.mail.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailSubjectTemplate {
    AUTH_CODE("[OhMobileGame] 회원가입 인증번호 입니다."),
    ;

    private final String subject;
}
