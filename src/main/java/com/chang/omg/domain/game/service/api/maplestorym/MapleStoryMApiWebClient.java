package com.chang.omg.domain.game.service.api.maplestorym;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.chang.omg.domain.game.service.api.maplestorym.dto.Character;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterBasic;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterGuild;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterItemEquipment;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterStat;
import com.chang.omg.global.config.property.MapleStoryMProperties;
import com.chang.omg.global.config.property.NexonCommonProperties;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class MapleStoryMApiWebClient implements MapleStoryMApi {

    private final NexonCommonProperties nexonCommonProperties;
    private final MapleStoryMProperties mapleStoryMProperties;
    private final HttpHeaders headers = new HttpHeaders();
    private final WebClient webClient;

    @PostConstruct
    private void init() {
        headers.add(nexonCommonProperties.getHeaderKey(), mapleStoryMProperties.getHeaderValue());
    }

    @Override
    public Character getCharacterOcid(final String characterName, final String worldName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(mapleStoryMProperties.getOcidApiUri())
                        .queryParam("character_name", "{characterName}")
                        .queryParam("world_name", "{worldName}")
                        .build(characterName, worldName))
                .header(nexonCommonProperties.getHeaderKey(), mapleStoryMProperties.getHeaderValue())
                .retrieve()
                .bodyToMono(Character.class)
                .block();
    }

    @Override
    public Mono<CharacterBasic> getCharacterBasic(final String ocid) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(mapleStoryMProperties.getBasicApiUri())
                        .queryParam("ocid", ocid)
                        .build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(CharacterBasic.class);
    }

    @Override
    public Mono<CharacterItemEquipment> getCharacterItem(final String ocid) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(mapleStoryMProperties.getItemApiUri())
                        .queryParam("ocid", ocid)
                        .build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(CharacterItemEquipment.class);
    }

    @Override
    public Mono<CharacterStat> getCharacterStat(final String ocid) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(mapleStoryMProperties.getStatApiUri())
                        .queryParam("ocid", ocid)
                        .build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(CharacterStat.class);
    }

    @Override
    public Mono<CharacterGuild> getCharacterGuild(final String ocid) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(mapleStoryMProperties.getGuildApiUri())
                        .queryParam("ocid", ocid)
                        .build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(CharacterGuild.class);
    }
}
