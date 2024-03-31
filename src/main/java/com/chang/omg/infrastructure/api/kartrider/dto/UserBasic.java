package com.chang.omg.infrastructure.api.kartrider.dto;

public record UserBasic(
        String racerName,
        String racerDateCreate,
        String racerDateLastLogin,
        String racerDateLastLogout,
        String racerLevel
) {

}
