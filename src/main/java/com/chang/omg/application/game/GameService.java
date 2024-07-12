package com.chang.omg.application.game;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.chang.omg.application.game.dto.RankingDataSave;
import com.chang.omg.domains.game.domain.GameCharacterSearchLog;
import com.chang.omg.domains.game.domain.GameType;
import com.chang.omg.domains.rank.domain.CharacterInfo;
import com.chang.omg.domains.rank.repository.GameRankingRedisRepository;
import com.chang.omg.global.exception.ApiException;
import com.chang.omg.global.exception.ApiExceptionCode;
import com.chang.omg.infrastructure.api.kartrider.KartRiderApi;
import com.chang.omg.infrastructure.api.kartrider.dto.User;
import com.chang.omg.infrastructure.api.kartrider.dto.UserBasic;
import com.chang.omg.infrastructure.api.kartrider.dto.UserTitleEquipment;
import com.chang.omg.infrastructure.api.maplestorym.MapleStoryMApi;
import com.chang.omg.infrastructure.api.maplestorym.dto.Character;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterBasic;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterGuild;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterItemEquipment;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterStat;
import com.chang.omg.presentation.game.dto.CharacterRankingResponse;
import com.chang.omg.presentation.game.dto.KartRiderUserInfoResponse;
import com.chang.omg.presentation.game.dto.MapleStoryMCharacterInfoResponse;

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
    private final GameRankingRedisRepository gameRankingRedisRepository;

    public MapleStoryMCharacterInfoResponse getMapleStoryMCharacterInfo(
            final String characterName,
            final String worldName
    ) {
        final Character character = mapleStoryMApi.getCharacterOcid(characterName, worldName); // Blocking

        saveGameCharacterSearchLog(GameType.MAPLESTORYM, worldName, characterName);

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
                    .join(); // Blocking on the combined future to get the final result
        });

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException exception) {
            log.error("ErrorTrace", exception);
            throw new ApiException(ApiExceptionCode.API_UNKNOWN_ERROR);
        }
    }

    public KartRiderUserInfoResponse getKartRiderUserInfo(final String userName) {
        final User user = kartRiderApi.getUserOuid(userName);
        final String ouid = user.ouidInfo().get(0).ouid();

        saveGameCharacterSearchLog(GameType.KARTRIDER, null, userName);

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
        }
    }

    private void saveGameCharacterSearchLog(
            final GameType gameType,
            final String worldName,
            final String characterName
    ) {
        final GameCharacterSearchLog gameCharacterSearchLog = GameCharacterSearchLog.builder()
                .gameType(gameType)
                .worldName(worldName)
                .characterName(characterName)
                .build();

        applicationEventPublisher.publishEvent(gameCharacterSearchLog);

        final CharacterInfo characterInfo = CharacterInfo.builder()
                .worldName(worldName)
                .characterName(characterName)
                .build();

        applicationEventPublisher.publishEvent(new RankingDataSave(gameType, characterInfo));
    }

    public List<CharacterRankingResponse> getGameCharacterSearchRank(final GameType gameType) {
        return gameRankingRedisRepository.findGameCharacterSearchRank(gameType);
    }
}
