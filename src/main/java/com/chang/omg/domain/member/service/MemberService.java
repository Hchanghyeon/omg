package com.chang.omg.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chang.omg.domain.member.controller.dto.MemberCreateRequest;
import com.chang.omg.domain.member.domain.Member;
import com.chang.omg.domain.member.domain.MemberRepository;
import com.chang.omg.domain.member.exception.MemberException;
import com.chang.omg.domain.member.exception.MemberExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(final MemberCreateRequest memberCreateRequest) {
        verifyEmailNotRegistered(memberCreateRequest.email());

        final String encryptedPassword = passwordEncoder.encode(memberCreateRequest.password());
        final Member member = memberCreateRequest.toEntity(encryptedPassword);

        final Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    private void verifyEmailNotRegistered(final String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(MemberExceptionCode.MEMBER_ALREADY_EXISTS, email);
        }
    }
}
