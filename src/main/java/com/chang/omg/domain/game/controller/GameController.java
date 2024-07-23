package com.chang.omg.domain.game.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chang.omg.domain.game.controller.dto.KartRiderUserInfoResponse;
import com.chang.omg.domain.game.controller.dto.MapleStoryMCharacterInfoResponse;
import com.chang.omg.domain.game.service.GameService;

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
}
