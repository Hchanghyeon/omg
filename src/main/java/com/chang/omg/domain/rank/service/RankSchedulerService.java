package com.chang.omg.domain.rank.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chang.omg.domain.game.domain.GameType;
import com.chang.omg.domain.rank.domain.GameRankingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankSchedulerService {

    private final GameRankingRepository gameRankingRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeRanking() {
        log.info("RankingRemoveScheduler start");

        for (GameType gameType : GameType.values()) {
            gameRankingRepository.removeRanking(gameType);
        }

        log.info("RankingRemoveScheduler end");
    }
}
