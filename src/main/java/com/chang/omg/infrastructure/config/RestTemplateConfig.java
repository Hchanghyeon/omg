package com.chang.omg.infrastructure.config;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.chang.omg.infrastructure.exception.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .messageConverters(getSnakeToCamelMessageConverter())
                .errorHandler(new RestTemplateResponseErrorHandler())
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    private MappingJackson2HttpMessageConverter getSnakeToCamelMessageConverter() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(SNAKE_CASE);

        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }
}
