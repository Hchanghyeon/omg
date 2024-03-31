package com.chang.omg.presentation.game;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chang.omg.application.game.GameService;
import com.chang.omg.domains.game.domain.GameCharacterSearchRank;
import com.chang.omg.domains.game.domain.GameType;
import com.chang.omg.presentation.game.dto.KartRiderUserInfoResponse;
import com.chang.omg.presentation.game.dto.MapleStoryMCharacterInfoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @GetMapping("/maplestorym")
    public ResponseEntity<MapleStoryMCharacterInfoResponse> getCharacterInfo(
            @RequestParam final String characterName,
            @RequestParam final String worldName
    ) {
        final MapleStoryMCharacterInfoResponse mapleStoryMCharacterInfoResponse = gameService.getMapleStoryMCharacterInfo(
                characterName,
                worldName
        );

        return ResponseEntity.ok(mapleStoryMCharacterInfoResponse);
    }

    @GetMapping("/kartrider")
    public ResponseEntity<KartRiderUserInfoResponse> getUserInfo(@RequestParam final String userName) {
        final KartRiderUserInfoResponse kartRiderUserInfoResponse = gameService.getKartRiderUserInfo(userName);

        return ResponseEntity.ok(kartRiderUserInfoResponse);
    }

    @GetMapping("/{gameType}/rank")
    public ResponseEntity<List<GameCharacterSearchRank>> getGameCharacterSearchRank(
            @PathVariable final GameType gameType) {
        return ResponseEntity.ok(gameService.getGameCharacterSearchRank(gameType));
    }
}
