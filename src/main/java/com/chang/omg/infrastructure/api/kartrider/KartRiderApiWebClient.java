package com.chang.omg.infrastructure.api.kartrider;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.chang.omg.global.config.property.KartRiderProperties;
import com.chang.omg.global.config.property.NexonCommonProperties;
import com.chang.omg.infrastructure.api.kartrider.dto.User;
import com.chang.omg.infrastructure.api.kartrider.dto.UserBasic;
import com.chang.omg.infrastructure.api.kartrider.dto.UserTitleEquipment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KartRiderApiWebClient implements KartRiderApi {

    private final NexonCommonProperties nexonCommonProperties;
    private final KartRiderProperties kartRiderProperties;
    private final HttpHeaders headers = new HttpHeaders();
    private final WebClient webClient;

    @PostConstruct
    private void init() {
        headers.add(nexonCommonProperties.getHeaderKey(), kartRiderProperties.getHeaderValue());
    }

    @Override
    public User getUserOuid(final String userName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(kartRiderProperties.getOuidApiUri())
                        .queryParam("racer_name", "{racerName}")
                        .build(userName))
                .header(nexonCommonProperties.getHeaderKey(), kartRiderProperties.getHeaderValue())
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    @Override
    public Mono<UserBasic> getUserBasic(final String ouid) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(kartRiderProperties.getBasicApiUri())
                        .queryParam("ouid", "{ouid}")
                        .build(ouid))
                .header(nexonCommonProperties.getHeaderKey(), kartRiderProperties.getHeaderValue())
                .retrieve()
                .bodyToMono(UserBasic.class);
    }

    @Override
    public Mono<UserTitleEquipment> getUserTitleEquipment(final String ouid) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(kartRiderProperties.getTitleApiUri())
                        .queryParam("ouid", "{ouid}")
                        .build(ouid))
                .header(nexonCommonProperties.getHeaderKey(), kartRiderProperties.getHeaderValue())
                .retrieve()
                .bodyToMono(UserTitleEquipment.class);
    }
}
