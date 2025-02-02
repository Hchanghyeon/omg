package com.chang.omg.domain.game.service.api.maplestorym;

import com.chang.omg.domain.game.service.api.maplestorym.dto.Character;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterBasic;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterGuild;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterItemEquipment;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterStat;

import reactor.core.publisher.Mono;

public interface MapleStoryMApi {

    Character getCharacterOcid(final String characterName, final String worldName);

    Mono<CharacterBasic> getCharacterBasic(final String ocid);

    Mono<CharacterItemEquipment> getCharacterItem(final String ocid);

    Mono<CharacterStat> getCharacterStat(final String ocid);

    Mono<CharacterGuild> getCharacterGuild(final String ocid);
}
