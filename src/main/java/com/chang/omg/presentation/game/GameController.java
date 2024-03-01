package com.chang.omg.presentation.game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chang.omg.application.game.GameService;
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
}
