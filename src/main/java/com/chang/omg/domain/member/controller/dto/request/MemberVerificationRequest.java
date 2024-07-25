package com.chang.omg.domain.member.controller.dto.request;

public record MemberVerificationRequest(String email, int authCode) {

}
