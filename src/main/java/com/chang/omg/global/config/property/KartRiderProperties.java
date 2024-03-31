package com.chang.omg.global.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "api.nexon.kartrider")
public class KartRiderProperties {

    private final String headerValue;
    private final String ouidApiUri;
    private final String basicApiUri;
    private final String titleApiUri;
}
