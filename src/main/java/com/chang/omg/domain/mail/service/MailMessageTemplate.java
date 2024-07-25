package com.chang.omg.domain.mail.service;

import java.text.MessageFormat;
import java.util.function.Function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailMessageTemplate {
    AUTH_CODE(email -> MessageFormat.format("회원가입 인증번호는 {0}입니다.", email)),
    ;

    private final Function<String, String> messageFormatter;

    public String getMessage(final String value) {
        return messageFormatter.apply(value);
    }
}
