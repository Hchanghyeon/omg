package com.chang.omg.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.chang.omg.global.config.property.CorsProperties;
import com.chang.omg.global.converter.GameTypeFromPathVariableConverter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsProperties.getUrls())
                .allowedMethods("OPTION", "GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(false);
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new GameTypeFromPathVariableConverter());
    }
}
