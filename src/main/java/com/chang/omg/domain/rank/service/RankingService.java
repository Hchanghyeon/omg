package com.chang.omg.domain.rank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chang.omg.domain.game.domain.GameType;
import com.chang.omg.domain.rank.controller.dto.CharacterRankingResponse;
import com.chang.omg.domain.rank.domain.GameRankingRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final GameRankingRedisRepository gameRankingRedisRepository;

    public List<CharacterRankingResponse> getGameCharacterSearchRank(final GameType gameType) {
        return gameRankingRedisRepository.findGameCharacterSearchRank(gameType);
    }
}
