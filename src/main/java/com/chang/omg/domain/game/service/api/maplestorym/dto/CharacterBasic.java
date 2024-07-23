package com.chang.omg.domain.game.service.api.maplestorym.dto;

public record CharacterBasic(
        String characterName,
        String worldName,
        String characterDateCreate,
        String characterDateLastLogin,
        String characterDateLastLogout,
        String characterJobName,
        String characterGender,
        String characterExp,
        String characterLevel
) {

}
