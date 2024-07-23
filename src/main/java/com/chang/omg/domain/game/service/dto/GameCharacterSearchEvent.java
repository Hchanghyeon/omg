package com.chang.omg.domain.game.service.dto;

import com.chang.omg.domain.game.domain.GameType;

import lombok.Builder;

@Builder
public record GameCharacterSearchEvent(GameType gameType, String worldName, String characterName) {

}
