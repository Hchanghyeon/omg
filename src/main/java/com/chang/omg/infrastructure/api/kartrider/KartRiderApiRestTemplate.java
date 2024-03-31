package com.chang.omg.infrastructure.api.kartrider;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.chang.omg.global.config.property.KartRiderProperties;
import com.chang.omg.global.config.property.NexonCommonProperties;
import com.chang.omg.infrastructure.api.kartrider.dto.User;
import com.chang.omg.infrastructure.api.kartrider.dto.UserBasic;
import com.chang.omg.infrastructure.api.kartrider.dto.UserTitleEquipment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KartRiderApiRestTemplate implements KartRiderApi {

    private final RestTemplate restTemplate;
    private final KartRiderProperties kartRiderProperties;
    private final NexonCommonProperties nexonCommonProperties;

    @Override
    public User getUserOuid(final String userName) {
        return restTemplate.exchange(
                        createOuidApiUri(userName),
                        HttpMethod.GET,
                        new HttpEntity<>(createHttpHeaders()),
                        User.class)
                .getBody();
    }

    private String createOuidApiUri(final String userName) {
        return UriComponentsBuilder.fromHttpUrl(
                        nexonCommonProperties.getBaseUrl() + kartRiderProperties.getOuidApiUri()
                )
                .queryParam("racer_name", userName)
                .build()
                .toUriString();
    }

    @Override
    public UserBasic getUserBasic(final String ouid) {
        return restTemplate.exchange(
                        createApiUrl(ouid, kartRiderProperties.getBasicApiUri()),
                        HttpMethod.GET,
                        new HttpEntity<>(createHttpHeaders()),
                        UserBasic.class)
                .getBody();
    }

    @Override
    public UserTitleEquipment getUserTitleEquipment(final String ouid) {
        return restTemplate.exchange(
                        createApiUrl(ouid, kartRiderProperties.getTitleApiUri()),
                        HttpMethod.GET,
                        new HttpEntity<>(createHttpHeaders()),
                        UserTitleEquipment.class)
                .getBody();
    }

    private String createApiUrl(final String ouid, final String requestUri) {
        return UriComponentsBuilder.fromHttpUrl(nexonCommonProperties.getBaseUrl() + requestUri)
                .queryParam("ouid", ouid)
                .build()
                .toUriString();
    }

    private HttpHeaders createHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(nexonCommonProperties.getHeaderKey(), kartRiderProperties.getHeaderValue());

        return headers;
    }
}
