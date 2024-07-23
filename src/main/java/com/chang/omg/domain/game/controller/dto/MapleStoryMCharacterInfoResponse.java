package com.chang.omg.domain.game.controller.dto;

import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterBasic;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterGuild;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterItemEquipment;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterStat;

public record MapleStoryMCharacterInfoResponse(
        CharacterBasic characterBasic,
        CharacterItemEquipment characterItemEquipment,
        CharacterStat characterStat,
        CharacterGuild characterGuild
) {

    public static MapleStoryMCharacterInfoResponse of(
            final CharacterBasic characterBasic,
            final CharacterItemEquipment characterItemEquipment,
            final CharacterStat characterStat,
            final CharacterGuild characterGuild
    ) {
        return new MapleStoryMCharacterInfoResponse(
                characterBasic,
                characterItemEquipment,
                characterStat,
                characterGuild
        );
    }
}
