package com.chang.omg.domain.rank.domain;

import java.util.List;

import com.chang.omg.domain.game.domain.GameType;
import com.chang.omg.domain.rank.controller.dto.CharacterRankingResponse;

public interface GameRankingRepository {

    void createOrIncrementScore(final GameType gameType, final CharacterInfo characterInfo);

    List<CharacterRankingResponse> findGameCharacterSearchRank(final GameType gameType);

    void removeRanking(final GameType gameType);
}
