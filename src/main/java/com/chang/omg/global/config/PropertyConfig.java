package com.chang.omg.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.chang.omg.global.config.property.AuthProperties;
import com.chang.omg.global.config.property.CorsProperties;
import com.chang.omg.global.config.property.KartRiderProperties;
import com.chang.omg.global.config.property.MapleStoryMProperties;
import com.chang.omg.global.config.property.NexonCommonProperties;
import com.chang.omg.global.config.property.RedisProperties;

@Configuration
@EnableConfigurationProperties(value = {
        CorsProperties.class,
        MapleStoryMProperties.class,
        KartRiderProperties.class,
        NexonCommonProperties.class,
        RedisProperties.class,
        AuthProperties.class
})
public class PropertyConfig {

}
