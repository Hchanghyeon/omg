package com.chang.omg.domain.game.service.api.kartrider.dto;

public record UserBasic(
        String racerName,
        String racerDateCreate,
        String racerDateLastLogin,
        String racerDateLastLogout,
        String racerLevel
) {

}
