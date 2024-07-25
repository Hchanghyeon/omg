package com.chang.omg.domain.member.controller.dto.request;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.chang.omg.domain.member.domain.Member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MemberCreateRequest(
        @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "이메일 양식 확인 필요") String email,
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "패스워드는 8자 이상이어야 하며, 숫자, 대문자, 소문자, 특수문자를 포함해야 합니다") String password,
        @NotBlank(message = "닉네임이 없거나 공백인 경우 확인 필요") @Length(min = 3, max = 20, message = "닉네임은 3글자 이상 20글자 이하") String nickname,
        @NotNull(message = "날짜가 없는 경우 확인 필요") LocalDate birthDate
) {

    public Member toEntity(final String encryptedPassword) {
        return Member.builder()
                .email(email)
                .password(encryptedPassword)
                .nickname(nickname)
                .birthDate(birthDate)
                .build();
    }
}
