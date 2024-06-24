package com.chang.omg.application.game;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.chang.omg.application.game.dto.RankingDataSave;
import com.chang.omg.domains.game.domain.GameCharacterSearchLog;
import com.chang.omg.domains.game.domain.GameType;
import com.chang.omg.domains.rank.domain.CharacterInfo;
import com.chang.omg.domains.rank.repository.GameRankingRedisRepository;
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
        final Character character = mapleStoryMApi.getCharacterOcid(characterName, worldName);
        final CharacterBasic characterBasic = mapleStoryMApi.getCharacterBasic(character.ocid());
        final CharacterItemEquipment characterItemEquipment = mapleStoryMApi.getCharacterItem(character.ocid());
        final CharacterStat characterStat = mapleStoryMApi.getCharacterStat(character.ocid());
        final CharacterGuild characterGuild = mapleStoryMApi.getCharacterGuild(character.ocid());

        saveGameCharacterSearchLog(GameType.MAPLESTORYM, worldName, characterName);

        return MapleStoryMCharacterInfoResponse.of(
                characterBasic,
                characterItemEquipment,
                characterStat,
                characterGuild
        );
    }

    public KartRiderUserInfoResponse getKartRiderUserInfo(final String userName) {
        final User user = kartRiderApi.getUserOuid(userName);
        final String ouid = user.ouidInfo().get(0).ouid();
        final UserBasic userBasic = kartRiderApi.getUserBasic(ouid);
        final UserTitleEquipment userTitleEquipment = kartRiderApi.getUserTitleEquipment(ouid);

        saveGameCharacterSearchLog(GameType.KARTRIDER, null, userBasic.racerName());

        return KartRiderUserInfoResponse.of(userBasic, userTitleEquipment);
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
