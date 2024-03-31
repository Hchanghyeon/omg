package com.chang.omg.infrastructure.api.maplestorym;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.chang.omg.global.config.property.MapleStoryMProperties;
import com.chang.omg.global.config.property.NexonCommonProperties;
import com.chang.omg.infrastructure.api.maplestorym.dto.Character;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterBasic;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterGuild;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterItemEquipment;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterStat;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MapleStoryMApiRestTemplate implements MapleStoryMApi {

    private final RestTemplate restTemplate;
    private final MapleStoryMProperties mapleStoryMProperties;
    private final NexonCommonProperties nexonCommonProperties;

    public Character getCharacterOcid(final String characterName, final String worldName) {
        return restTemplate.exchange(
                        createOcidApiUri(characterName, worldName),
                        HttpMethod.GET,
                        new HttpEntity<>(createHttpHeaders()),
                        Character.class)
                .getBody();
    }

    private String createOcidApiUri(final String characterName, final String worldName) {
        return UriComponentsBuilder.fromHttpUrl(
                        nexonCommonProperties.getBaseUrl() + mapleStoryMProperties.getOcidApiUri()
                )
                .queryParam("character_name", characterName)
                .queryParam("world_name", worldName)
                .build()
                .toUriString();
    }

    @Override
    public CharacterBasic getCharacterBasic(final String ocid) {
        return restTemplate.exchange(
                        createApiUrl(ocid, mapleStoryMProperties.getBasicApiUri()),
                        HttpMethod.GET,
                        new HttpEntity<>(createHttpHeaders()),
                        CharacterBasic.class)
                .getBody();
    }

    @Override
    public CharacterItemEquipment getCharacterItem(final String ocid) {
        return restTemplate.exchange(
                        createApiUrl(ocid, mapleStoryMProperties.getItemApiUri()),
                        HttpMethod.GET,
                        new HttpEntity<>(createHttpHeaders()),
                        CharacterItemEquipment.class)
                .getBody();
    }

    @Override
    public CharacterStat getCharacterStat(final String ocid) {
        return restTemplate.exchange(
                        createApiUrl(ocid, mapleStoryMProperties.getStatApiUri()),
                        HttpMethod.GET,
                        new HttpEntity<>(createHttpHeaders()),
                        CharacterStat.class)
                .getBody();
    }

    @Override
    public CharacterGuild getCharacterGuild(final String ocid) {
        return restTemplate.exchange(
                        createApiUrl(ocid, mapleStoryMProperties.getGuildApiUri()),
                        HttpMethod.GET,
                        new HttpEntity<>(createHttpHeaders()),
                        CharacterGuild.class)
                .getBody();
    }

    private String createApiUrl(final String ocid, final String requestUri) {
        return UriComponentsBuilder.fromHttpUrl(nexonCommonProperties.getBaseUrl() + requestUri)
                .queryParam("ocid", ocid)
                .build()
                .toUriString();
    }

    private HttpHeaders createHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(nexonCommonProperties.getHeaderKey(), mapleStoryMProperties.getHeaderValue());

        return headers;
    }
}
