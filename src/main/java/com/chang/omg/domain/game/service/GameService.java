package com.chang.omg.domain.game.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.chang.omg.domain.game.controller.dto.KartRiderUserInfoResponse;
import com.chang.omg.domain.game.controller.dto.MapleStoryMCharacterInfoResponse;
import com.chang.omg.domain.game.domain.GameType;
import com.chang.omg.domain.game.service.api.kartrider.KartRiderApi;
import com.chang.omg.domain.game.service.api.kartrider.dto.User;
import com.chang.omg.domain.game.service.api.kartrider.dto.UserBasic;
import com.chang.omg.domain.game.service.api.kartrider.dto.UserTitleEquipment;
import com.chang.omg.domain.game.service.api.maplestorym.MapleStoryMApi;
import com.chang.omg.domain.game.service.api.maplestorym.dto.Character;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterBasic;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterGuild;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterItemEquipment;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterStat;
import com.chang.omg.domain.game.service.dto.GameCharacterSearchEvent;
import com.chang.omg.domain.rank.domain.CharacterInfo;
import com.chang.omg.domain.rank.service.dto.GameRankingUpdateEvent;
import com.chang.omg.global.exception.ApiException;
import com.chang.omg.global.exception.ApiExceptionCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final KartRiderApi kartRiderApi;
    private final MapleStoryMApi mapleStoryMApi;
    private final ApplicationEventPublisher applicationEventPublisher;

    public MapleStoryMCharacterInfoResponse getMapleStoryMCharacterInfo(
            final String characterName,
            final String worldName
    ) {
        final Character character = mapleStoryMApi.getCharacterOcid(characterName, worldName); // Blocking

        final CompletableFuture<MapleStoryMCharacterInfoResponse> future = CompletableFuture.supplyAsync(() -> {
            final Mono<CharacterBasic> characterBasic = mapleStoryMApi.getCharacterBasic(character.ocid());
            final Mono<CharacterItemEquipment> characterItemEquipment = mapleStoryMApi.getCharacterItem(
                    character.ocid());
            final Mono<CharacterStat> characterStat = mapleStoryMApi.getCharacterStat(character.ocid());
            final Mono<CharacterGuild> characterGuild = mapleStoryMApi.getCharacterGuild(character.ocid());

            return Mono.zip(characterBasic, characterItemEquipment, characterStat, characterGuild)
                    .map(tuple -> MapleStoryMCharacterInfoResponse.of(
                            tuple.getT1(),
                            tuple.getT2(),
                            tuple.getT3(),
                            tuple.getT4()
                    ))
                    .toFuture()
                    .join();
        });

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException exception) {
            log.error("ErrorTrace", exception);
            throw new ApiException(ApiExceptionCode.API_UNKNOWN_ERROR);
        } finally {
            saveGameCharacterSearchLog(GameType.MAPLESTORYM, worldName, characterName);
        }
    }

    public KartRiderUserInfoResponse getKartRiderUserInfo(final String userName) {
        final User user = kartRiderApi.getUserOuid(userName);
        final String ouid = user.ouidInfo().get(0).ouid();

        final CompletableFuture<KartRiderUserInfoResponse> future = CompletableFuture.supplyAsync(() -> {
            final Mono<UserBasic> userBasic = kartRiderApi.getUserBasic(ouid);
            final Mono<UserTitleEquipment> userTitleEquipment = kartRiderApi.getUserTitleEquipment(ouid);

            return Mono.zip(userBasic, userTitleEquipment)
                    .map(tuple -> KartRiderUserInfoResponse.of(tuple.getT1(), tuple.getT2()))
                    .toFuture()
                    .join();
        });

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException exception) {
            log.error("ErrorTrace", exception);
            throw new ApiException(ApiExceptionCode.API_UNKNOWN_ERROR);
        } finally {
            saveGameCharacterSearchLog(GameType.KARTRIDER, null, userName);
        }
    }

    private void saveGameCharacterSearchLog(
            final GameType gameType,
            final String worldName,
            final String characterName
    ) {
        applicationEventPublisher.publishEvent(
                GameCharacterSearchEvent.builder()
                        .gameType(gameType)
                        .worldName(worldName)
                        .characterName(characterName)
        );

        final CharacterInfo characterInfo = CharacterInfo.builder()
                .worldName(worldName)
                .characterName(characterName)
                .build();

        applicationEventPublisher.publishEvent(
                GameRankingUpdateEvent.builder()
                        .gameType(gameType)
                        .characterInfo(characterInfo)
                        .build()
        );
    }
}
