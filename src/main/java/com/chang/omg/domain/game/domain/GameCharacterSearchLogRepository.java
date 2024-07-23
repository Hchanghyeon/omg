package com.chang.omg.domain.game.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chang.omg.domain.game.controller.dto.CharacterRankingResponse;

public interface GameCharacterSearchLogRepository extends JpaRepository<GameCharacterSearchLog, Long> {

    @Deprecated
    @Query("""
            SELECT new com.chang.omg.presentation.game.dto.CharacterRankingResponse
            (g.worldName, g.characterName, COUNT(g.characterName))
            FROM GameCharacterSearchLog g 
            WHERE g.createdAt > CURRENT_DATE AND g.gameType =:gameType
            GROUP BY g.characterName, g.worldName 
            ORDER BY COUNT(g.characterName) DESC
            LIMIT 5
            """)
    List<CharacterRankingResponse> findGameCharacterSearchRank(final GameType gameType);
}
