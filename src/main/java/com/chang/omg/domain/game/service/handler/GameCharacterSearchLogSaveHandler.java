package com.chang.omg.domain.game.service.handler;

import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chang.omg.domain.game.domain.GameCharacterSearchLog;
import com.chang.omg.domain.game.domain.GameCharacterSearchLogRepository;
import com.chang.omg.domain.game.service.dto.GameCharacterSearchEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameCharacterSearchLogSaveHandler {

    private final GameCharacterSearchLogRepository gameCharacterSearchLogRepository;

    @Async
    @EventListener
    @Transactional
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 10000),
            listeners = "retryHandler"
    )
    public void saveGameCharacterSearchLog(final GameCharacterSearchEvent gameCharacterSearchEvent) {
        final GameCharacterSearchLog gameCharacterSearchLog = GameCharacterSearchLog.builder()
                .gameType(gameCharacterSearchEvent.gameType())
                .worldName(gameCharacterSearchEvent.worldName())
                .characterName(gameCharacterSearchEvent.characterName())
                .build();

        gameCharacterSearchLogRepository.save(gameCharacterSearchLog);
        log.info("game character search log saved : {}", gameCharacterSearchLog.getCharacterName());
    }
}
