package com.chang.omg.global.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "api.nexon.common")
public class NexonCommonProperties {

    private final String baseUrl;
    private final String headerKey;
}
