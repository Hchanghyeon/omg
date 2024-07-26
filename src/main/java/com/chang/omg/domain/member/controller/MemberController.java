package com.chang.omg.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chang.omg.domain.auth.security.JwtTokenExtractor;
import com.chang.omg.domain.auth.security.JwtTokenProvider;
import com.chang.omg.domain.auth.security.TokenType;
import com.chang.omg.domain.member.controller.dto.request.MemberAuthCodeRequest;
import com.chang.omg.domain.member.controller.dto.request.MemberCreateRequest;
import com.chang.omg.domain.member.controller.dto.request.MemberVerificationRequest;
import com.chang.omg.domain.member.controller.dto.response.MemberCreateResponse;
import com.chang.omg.domain.member.controller.dto.response.MemberRegisterTokenResponse;
import com.chang.omg.domain.member.exception.MemberException;
import com.chang.omg.domain.member.exception.MemberExceptionCode;
import com.chang.omg.domain.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenExtractor jwtTokenExtractor;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberCreateResponse> createMember(
            @RequestBody @Valid final MemberCreateRequest memberCreateRequest,
            @RequestHeader final String authorization
    ) {
        final String registerToken = jwtTokenExtractor.extractToken(authorization);
        final String tokenType = jwtTokenProvider.getSubject(registerToken);
        final String tokenEmail = jwtTokenProvider.getClaim(registerToken, "email");

        if (!tokenType.equals(TokenType.REGISTER.toString()) || !tokenEmail.equals(memberCreateRequest.email())) {
            throw new MemberException(MemberExceptionCode.MEMBER_NOT_MATCHED_TOKEN_DETAILS);
        }

        final Long memberId = memberService.createMember(memberCreateRequest);

        return ResponseEntity.ok(new MemberCreateResponse(memberId));
    }

    @PostMapping("/verification-auth-code")
    public ResponseEntity<Void> createAuthCodeAndSendEmail(
            @RequestBody final MemberAuthCodeRequest memberAuthCodeRequest
    ) {
        memberService.createAuthCodeAndSendEmail(memberAuthCodeRequest);

        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/verification-auth-code")
    public ResponseEntity<MemberRegisterTokenResponse> verifyAuthCode(
            @ModelAttribute final MemberVerificationRequest memberVerificationRequest
    ) {
        final String registerToken = memberService.verifyAuthCode(memberVerificationRequest);

        return ResponseEntity.ok(new MemberRegisterTokenResponse(registerToken));
    }
}
