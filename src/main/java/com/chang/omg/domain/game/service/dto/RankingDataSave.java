package com.chang.omg.domain.game.service.dto;

import com.chang.omg.domain.game.domain.GameType;
import com.chang.omg.domain.rank.domain.CharacterInfo;

public record RankingDataSave(GameType gameType, CharacterInfo characterInfo) {

}
