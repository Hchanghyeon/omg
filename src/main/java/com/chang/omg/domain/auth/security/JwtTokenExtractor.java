package com.chang.omg.domain.auth.security;

import org.springframework.stereotype.Component;

import com.chang.omg.domain.auth.exception.AuthException;
import com.chang.omg.domain.auth.exception.AuthExceptionCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenExtractor {

    private static final String BEARER_TYPE = "Bearer ";

    public String extractToken(final String header) {
        if (header != null && header.startsWith(BEARER_TYPE)) {
            return header.substring(BEARER_TYPE.length()).trim();
        }

        throw new AuthException(AuthExceptionCode.AUTH_INVALID_TOKEN, header);
    }
}
