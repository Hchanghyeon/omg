package com.chang.omg.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chang.omg.domain.member.controller.dto.request.MemberAuthCodeRequest;
import com.chang.omg.domain.member.controller.dto.request.MemberCreateRequest;
import com.chang.omg.domain.member.controller.dto.request.MemberVerificationRequest;
import com.chang.omg.domain.member.controller.dto.response.MemberCreateResponse;
import com.chang.omg.domain.member.controller.dto.response.MemberVeriticationStatusResponse;
import com.chang.omg.domain.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberCreateResponse> createMember(
            @RequestBody @Valid final MemberCreateRequest memberCreateRequest
    ) {
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
    public ResponseEntity<MemberVeriticationStatusResponse> verifyAuthCode(
            @ModelAttribute MemberVerificationRequest memberVerificationRequest
    ) {
        final boolean isVerification = memberService.verifyAuthCode(memberVerificationRequest);

        return ResponseEntity.ok(new MemberVeriticationStatusResponse(isVerification));
    }
}
