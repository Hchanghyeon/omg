package com.chang.omg.domain.member.domain;

public interface MemberAuthCodeRepository {

    void saveAuthCodeWithEmail(final int authCode, final String memberEmail);

    boolean existsAuthCodeByEmail(final String memberEmail);

    int findAuthCodeByEmail(final String memberEmail);
}
