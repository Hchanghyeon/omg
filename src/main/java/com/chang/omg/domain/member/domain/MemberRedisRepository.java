package com.chang.omg.domain.member.domain;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.chang.omg.domain.member.exception.MemberException;
import com.chang.omg.domain.member.exception.MemberExceptionCode;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRedisRepository {

    private static final String PREFIX_AUTH_CODE = "AUTH:";
    private static final int AUTH_CODE_TTL = 121;
    private final RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;

    @PostConstruct
    public void initialize() {
        valueOperations = redisTemplate.opsForValue();
    }

    public void saveAuthCodeWithEmail(final int authCode, final String memberEmail) {
        valueOperations.set(PREFIX_AUTH_CODE + memberEmail, authCode, AUTH_CODE_TTL, TimeUnit.SECONDS);
    }

    public boolean existsAuthCodeByEmail(final String memberEmail) {
        final Object authCode = valueOperations.get(PREFIX_AUTH_CODE + memberEmail);

        return !Objects.isNull(authCode);
    }

    public int findAuthCodeByEmail(final String memberEmail) {
        final Object authCode = valueOperations.get(PREFIX_AUTH_CODE + memberEmail);

        if (Objects.isNull(authCode)) {
            throw new MemberException(MemberExceptionCode.MEMBER_AUTH_CODE_NOT_FOUND, memberEmail);
        }

        return (int)authCode;
    }
}
