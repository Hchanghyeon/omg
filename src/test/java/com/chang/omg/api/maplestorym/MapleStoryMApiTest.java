package com.chang.omg.api.maplestorym;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import com.chang.omg.api.IntegrationApiTest;
import com.chang.omg.fixture.dto.maplestorym.MapleStoryMDtoFixtures;
import com.chang.omg.global.config.property.MapleStoryMProperties;
import com.chang.omg.infrastructure.api.maplestorym.MapleStoryMApi;
import com.chang.omg.infrastructure.api.maplestorym.MapleStoryMApiRestTemplate;
import com.chang.omg.infrastructure.api.maplestorym.dto.Character;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterBasic;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterGuild;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterItemEquipment;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterStat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest(value = MapleStoryMApiRestTemplate.class)
class MapleStoryMApiTest extends IntegrationApiTest {

    @Autowired
    private MapleStoryMApi mapleStoryMApi;

    @Autowired
    private MapleStoryMProperties mapleStoryMProperties;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(SNAKE_CASE);

    private static final String characterName = "황창구리";
    private static final String worldName = "스카니아";
    private static final String ocid = "1eab45c6s2c4d56e1d5c7e";

    @Test
    @DisplayName("캐릭터 명과 월드 명으로 요청했을 때 캐릭터의 Ocid를 반환한다.")
    void returnCharacterOcidWithCharacterNameAndWorldName() throws JsonProcessingException {
        // given
        final String expectedApiUrl = UriComponentsBuilder.fromHttpUrl(
                        nexonCommonProperties.getBaseUrl() + mapleStoryMProperties.getOcidApiUri()
                )
                .queryParam("character_name", characterName)
                .queryParam("world_name", worldName)
                .toUriString();

        final Character expectedCharacter = MapleStoryMDtoFixtures.characterBuild();
        final String expectedResponse = objectMapper.writeValueAsString(expectedCharacter);

        mockRestServiceServer.expect(requestTo(expectedApiUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        // when
        final Character character = mapleStoryMApi.getCharacterOcid(characterName, worldName);

        // then
        assertThat(character.ocid()).isEqualTo(expectedCharacter.ocid());

        mockRestServiceServer.verify();
    }

    @Test
    @DisplayName("캐릭터의 Ocid로 요청했을 때 캐릭터의 기본 정보를 반환한다.")
    void returnCharacterBasicWithCharacterOcid() throws JsonProcessingException {
        // given
        final String expectedApiUrl = UriComponentsBuilder.fromHttpUrl(
                        nexonCommonProperties.getBaseUrl() + mapleStoryMProperties.getBasicApiUri()
                )
                .queryParam("ocid", ocid)
                .toUriString();

        final CharacterBasic expectedCharacterBasic = MapleStoryMDtoFixtures.characterBasicBuild();
        final String expectedResponse = objectMapper.writeValueAsString(expectedCharacterBasic);

        mockRestServiceServer.expect(requestTo(expectedApiUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        // when
        final CharacterBasic characterBasic = mapleStoryMApi.getCharacterBasic(ocid);

        // then
        assertThat(characterBasic.characterName()).isEqualTo(expectedCharacterBasic.characterName());
        assertThat(characterBasic.worldName()).isEqualTo(expectedCharacterBasic.worldName());
        assertThat(characterBasic.characterDateCreate()).isEqualTo(expectedCharacterBasic.characterDateCreate());
        assertThat(characterBasic.characterDateLastLogin()).isEqualTo(expectedCharacterBasic.characterDateLastLogin());
        assertThat(characterBasic.characterDateLastLogout()).isEqualTo(
                expectedCharacterBasic.characterDateLastLogout());
        assertThat(characterBasic.characterJobName()).isEqualTo(expectedCharacterBasic.characterJobName());
        assertThat(characterBasic.characterGender()).isEqualTo(expectedCharacterBasic.characterGender());
        assertThat(characterBasic.characterExp()).isEqualTo(expectedCharacterBasic.characterExp());
        assertThat(characterBasic.characterLevel()).isEqualTo(expectedCharacterBasic.characterLevel());

        mockRestServiceServer.verify();
    }

    @Test
    @DisplayName("캐릭터의 Ocid로 요청했을 때 캐릭터의 아이템 정보들을 불러올 수 있다.")
    void returnCharacterItemEquipmentWithCharacterOcid() throws JsonProcessingException {
        // given
        final String expectedApiUrl = UriComponentsBuilder.fromHttpUrl(
                        nexonCommonProperties.getBaseUrl() + mapleStoryMProperties.getItemApiUri()
                )
                .queryParam("ocid", ocid)
                .toUriString();

        final CharacterItemEquipment expectedCharacterItemEquipment = MapleStoryMDtoFixtures.characterItemEquipmentBuild();
        final String expectedResponse = objectMapper.writeValueAsString(expectedCharacterItemEquipment);

        mockRestServiceServer.expect(requestTo(expectedApiUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        // when
        final CharacterItemEquipment characterItemEquipment = mapleStoryMApi.getCharacterItem(ocid);

        // then
        assertThat(characterItemEquipment).usingRecursiveComparison().isEqualTo(expectedCharacterItemEquipment);

        mockRestServiceServer.verify();
    }

    @Test
    @DisplayName("캐릭터의 Ocid로 요청했을 때 캐릭터의 스텟을 불러올 수 있다.")
    void returnCharacterStatWithCharacterOcid() throws JsonProcessingException {
        // given
        final String expectedApiUrl = UriComponentsBuilder.fromHttpUrl(
                        nexonCommonProperties.getBaseUrl() + mapleStoryMProperties.getStatApiUri()
                )
                .queryParam("ocid", ocid)
                .toUriString();

        final CharacterStat expectedCharacterStat = MapleStoryMDtoFixtures.characterStat();
        final String expectedResponse = objectMapper.writeValueAsString(expectedCharacterStat);

        mockRestServiceServer.expect(requestTo(expectedApiUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        // when
        final CharacterStat characterStat = mapleStoryMApi.getCharacterStat(ocid);

        // then
        assertThat(characterStat).usingRecursiveComparison().isEqualTo(expectedCharacterStat);

        mockRestServiceServer.verify();
    }

    @Test
    @DisplayName("캐릭터의 Ocid로 요청했을 때 캐릭터의 길드 정보를 불러올 수 있다.")
    void returnCharacterBuildWithCharacterOcid() throws JsonProcessingException {
        // given
        final String expectedApiUrl = UriComponentsBuilder.fromHttpUrl(
                        nexonCommonProperties.getBaseUrl() + mapleStoryMProperties.getGuildApiUri()
                )
                .queryParam("ocid", ocid)
                .toUriString();

        final CharacterGuild expectedCharacterBasic = MapleStoryMDtoFixtures.characterGuildBuild();
        final String expectedResponse = objectMapper.writeValueAsString(expectedCharacterBasic);

        mockRestServiceServer.expect(requestTo(expectedApiUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        // when
        final CharacterGuild characterGuild = mapleStoryMApi.getCharacterGuild(ocid);

        // then
        assertThat(characterGuild.guildName()).isEqualTo(expectedCharacterBasic.guildName());

        mockRestServiceServer.verify();
    }
}
