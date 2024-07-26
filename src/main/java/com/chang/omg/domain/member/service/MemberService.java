package com.chang.omg.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chang.omg.domain.auth.security.JwtTokenProvider;
import com.chang.omg.domain.mail.service.MailMessageTemplate;
import com.chang.omg.domain.mail.service.MailService;
import com.chang.omg.domain.mail.service.MailSubjectTemplate;
import com.chang.omg.domain.mail.service.dto.EmailDetails;
import com.chang.omg.domain.member.controller.dto.request.MemberAuthCodeRequest;
import com.chang.omg.domain.member.controller.dto.request.MemberCreateRequest;
import com.chang.omg.domain.member.controller.dto.request.MemberVerificationRequest;
import com.chang.omg.domain.member.domain.Member;
import com.chang.omg.domain.member.domain.MemberRedisRepository;
import com.chang.omg.domain.member.domain.MemberRepository;
import com.chang.omg.domain.member.exception.MemberException;
import com.chang.omg.domain.member.exception.MemberExceptionCode;
import com.chang.omg.global.util.RandomUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberRedisRepository memberRedisRepository;

    @Transactional
    public Long createMember(final MemberCreateRequest memberCreateRequest) {
        verifyEmailAlreadyRegistered(memberCreateRequest.email());

        final String encryptedPassword = passwordEncoder.encode(memberCreateRequest.password());
        final Member member = memberCreateRequest.toEntity(encryptedPassword);

        final Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    private void verifyEmailAlreadyRegistered(final String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(MemberExceptionCode.MEMBER_ALREADY_EXISTS, email);
        }
    }

    @Transactional
    public void createAuthCodeAndSendEmail(final MemberAuthCodeRequest memberAuthCodeRequest) {
        final String memberEmail = memberAuthCodeRequest.email();
        verifyAuthCodeAlreadySaved(memberEmail);

        final int authCode = RandomUtils.createAuthCode();
        memberRedisRepository.saveAuthCodeWithEmail(authCode, memberEmail);

        mailService.sendEmail(
                new EmailDetails(
                        MailSubjectTemplate.AUTH_CODE.getSubject(),
                        MailMessageTemplate.AUTH_CODE.getMessage(String.valueOf(authCode)),
                        memberEmail
                )
        );
    }

    private void verifyAuthCodeAlreadySaved(final String memberEmail) {
        final boolean isAuthCodeExists = memberRedisRepository.existsAuthCodeByEmail(memberEmail);

        if (isAuthCodeExists) {
            throw new MemberException(MemberExceptionCode.MEMBER_ALREADY_GET_AUTH_CODE, memberEmail);
        }
    }

    public String verifyAuthCode(final MemberVerificationRequest memberVerificationRequest) {
        final String email = memberVerificationRequest.email();
        final int authCode = memberRedisRepository.findAuthCodeByEmail(email);

        if (authCode != memberVerificationRequest.authCode()) {
            throw new MemberException(MemberExceptionCode.MEMBER_AUTH_CODE_NOT_MATCHED);
        }

        return jwtTokenProvider.createRegisterToken(email);
    }
}
