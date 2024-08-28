package com.chang.omg.domain.rank.service.handler;

import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.chang.omg.domain.rank.domain.GameRankingRepository;
import com.chang.omg.domain.rank.service.dto.GameRankingUpdateEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GameRankingDataSaveHandler {

    private final GameRankingRepository gameRankingRepository;

    @Async
    @EventListener
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 10000),
            listeners = "retryHandler"
    )
    public void gameRankingDataSave(final GameRankingUpdateEvent gameRankingUpdateEvent) {
        gameRankingRepository.createOrIncrementScore(gameRankingUpdateEvent.gameType(),
                gameRankingUpdateEvent.characterInfo());
    }
}
