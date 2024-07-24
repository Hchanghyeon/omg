package com.chang.omg.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chang.omg.domain.member.controller.dto.MemberCreateRequest;
import com.chang.omg.domain.member.controller.dto.MemberCreateResponse;
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
            @RequestBody @Valid final MemberCreateRequest memberCreateRequest) {
        final Long memberId = memberService.createMember(memberCreateRequest);

        return ResponseEntity.ok(new MemberCreateResponse(memberId));
    }
}
