package com.chang.omg.domain.member.domain;

import java.time.LocalDate;

import org.springframework.util.StringUtils;

import com.chang.omg.common.domain.BaseEntity;
import com.chang.omg.domain.member.exception.MemberException;
import com.chang.omg.domain.member.exception.MemberExceptionCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false, length = 320)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", unique = true, nullable = false, length = 20)
    private String nickname;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    private static final int MAX_EMAIL_LENGTH = 320;
    private static final int MAX_NICKNAME_LENGTH = 20;

    @Builder
    public Member(final String email, final String password, final String nickname, final LocalDate birthDate) {
        this.email = validateEmail(email);
        this.password = password;
        this.nickname = validateNickname(nickname);
        this.birthDate = birthDate;
    }

    private String validateEmail(final String email) {
        if (!StringUtils.hasText(email) || email.length() > MAX_EMAIL_LENGTH) {
            throw new MemberException(MemberExceptionCode.MEMBER_CREATE_FAIL, email);
        }

        return email;
    }

    private String validateNickname(final String nickname) {
        if (!StringUtils.hasText(nickname) || nickname.length() > MAX_NICKNAME_LENGTH) {
            throw new MemberException(MemberExceptionCode.MEMBER_CREATE_FAIL, nickname);
        }

        return nickname;
    }
}
