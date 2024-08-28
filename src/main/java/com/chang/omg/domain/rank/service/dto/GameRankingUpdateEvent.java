package com.chang.omg.domain.rank.service.dto;

import com.chang.omg.domain.game.domain.GameType;
import com.chang.omg.domain.rank.domain.CharacterInfo;

import lombok.Builder;

@Builder
public record GameRankingUpdateEvent(GameType gameType, CharacterInfo characterInfo) {

}
