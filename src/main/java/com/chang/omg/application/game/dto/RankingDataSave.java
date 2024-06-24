package com.chang.omg.application.game.dto;

import com.chang.omg.domains.game.domain.GameType;
import com.chang.omg.domains.rank.domain.CharacterInfo;

public record RankingDataSave(GameType gameType, CharacterInfo characterInfo) {

}
