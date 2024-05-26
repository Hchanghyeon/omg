package com.chang.omg.domains.rank.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

import com.chang.omg.domains.game.domain.GameType;
import com.chang.omg.domains.rank.domain.CharacterInfo;
import com.chang.omg.presentation.game.dto.CharacterRankingResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GameRankingRedisRepository {

    private static final int INCREMENT_NUMBER = 1;
    private static final int RANK_RANGE_MIN = 0;
    private static final int RANK_RANGE_MAX = 4;

    private final RedisTemplate<String, Object> redisTemplate;
    private ZSetOperations<String, Object> zSetOperations;

    @PostConstruct
    public void initialize() {
        zSetOperations = redisTemplate.opsForZSet();
    }

    public void createOrIncrementScore(final GameType gameType, final CharacterInfo characterInfo) {
        zSetOperations.incrementScore(gameType.toString(), characterInfo, INCREMENT_NUMBER);
    }

    public List<CharacterRankingResponse> findGameCharacterSearchRank(final GameType gameType) {
        final Set<TypedTuple<Object>> characterRanking = zSetOperations.reverseRangeWithScores(
                gameType.toString(), RANK_RANGE_MIN, RANK_RANGE_MAX);

        return characterRanking.stream()
                .map(character -> {
                    final CharacterInfo characterInfo = (CharacterInfo)character.getValue();

                    return new CharacterRankingResponse(
                            characterInfo.worldName(),
                            characterInfo.characterName(),
                            character.getScore().longValue()
                    );
                })
                .toList();
    }
}
