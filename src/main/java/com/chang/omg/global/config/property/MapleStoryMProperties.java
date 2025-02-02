package com.chang.omg.global.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "api.nexon.maplestorym")
public class MapleStoryMProperties {
    
    private final String headerValue;
    private final String ocidApiUri;
    private final String basicApiUri;
    private final String itemApiUri;
    private final String statApiUri;
    private final String guildApiUri;
}
