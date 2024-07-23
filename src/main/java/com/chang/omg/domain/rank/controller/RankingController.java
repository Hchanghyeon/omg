package com.chang.omg.domain.rank.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chang.omg.domain.game.domain.GameType;
import com.chang.omg.domain.rank.controller.dto.CharacterRankingResponse;
import com.chang.omg.domain.rank.service.RankingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rank")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/{gameType}")
    public ResponseEntity<List<CharacterRankingResponse>> getGameCharacterSearchRank(
            @PathVariable final GameType gameType) {
        return ResponseEntity.ok(rankingService.getGameCharacterSearchRank(gameType));
    }
}
