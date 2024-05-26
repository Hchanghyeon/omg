package com.chang.omg.application.rank;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chang.omg.domains.game.domain.GameType;
import com.chang.omg.domains.rank.repository.GameRankingRedisRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankSchedulerService {

    private final GameRankingRedisRepository gameRankingRedisRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeRanking() {
        log.info("RankingRemoveScheduler start");

        for (GameType gameType : GameType.values()) {
            gameRankingRedisRepository.removeRanking(gameType);
        }

        log.info("RankingRemoveScheduler end");
    }
}
