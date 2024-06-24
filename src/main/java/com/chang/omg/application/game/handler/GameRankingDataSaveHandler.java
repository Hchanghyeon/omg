package com.chang.omg.application.game.handler;

import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chang.omg.application.game.dto.RankingDataSave;
import com.chang.omg.domains.rank.repository.GameRankingRedisRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GameRankingDataSaveHandler {

    private final GameRankingRedisRepository gameRankingRedisRepository;

    @Async
    @EventListener
    @Transactional
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 10000),
            listeners = "retryHandler"
    )
    public void gameRankingDataSave(final RankingDataSave rankingDataSave) {
        gameRankingRedisRepository.createOrIncrementScore(rankingDataSave.gameType(), rankingDataSave.characterInfo());
    }
}
