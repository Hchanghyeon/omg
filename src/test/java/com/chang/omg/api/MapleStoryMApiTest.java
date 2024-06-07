package com.chang.omg.api;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import com.chang.omg.global.config.PropertyConfig;
import com.chang.omg.global.config.property.MapleStoryMProperties;
import com.chang.omg.global.config.property.NexonCommonProperties;
import com.chang.omg.infrastructure.api.maplestorym.MapleStoryMApi;
import com.chang.omg.infrastructure.api.maplestorym.MapleStoryMApiRestTemplate;
import com.chang.omg.infrastructure.api.maplestorym.dto.Character;

@Import({RestTemplateTestConfig.class, PropertyConfig.class})
@RestClientTest(value = MapleStoryMApiRestTemplate.class)
class MapleStoryMApiTest {

    @Autowired
    private MapleStoryMApi mapleStoryMApiRestTemplate;

    @Autowired
    private MapleStoryMProperties mapleStoryMProperties;

    @Autowired
    private NexonCommonProperties nexonCommonProperties;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    private static final String characterName = "황창구리";
    private static final String worldName = "스카니아";
    private static final String userOcid = "1eab45c6s2c4d56e1d5c7e";
    private static final String encodedCharacterName = URLEncoder.encode(characterName, StandardCharsets.UTF_8);
    private static final String encodedWorldName = URLEncoder.encode(worldName, StandardCharsets.UTF_8);

    @Test
    @DisplayName("캐릭터의 Ocid를 가져온다.")
    void getCharacterOcid() {
        // given
        final String expectedApiUrl =
                nexonCommonProperties.getBaseUrl() + mapleStoryMProperties.getOcidApiUri() +
                        "?character_name=" + encodedCharacterName + "&world_name=" + encodedWorldName;
        final String expectedResponse = "{\"ocid\":\"" + userOcid + "\"}";

        mockRestServiceServer.expect(requestTo(expectedApiUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        // when
        final Character character = mapleStoryMApiRestTemplate.getCharacterOcid(characterName, worldName);

        // then
        assertThat(character.ocid()).isEqualTo(userOcid);
    }
}
