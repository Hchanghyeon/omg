package com.chang.omg.global.config;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;
import static org.springframework.http.MediaType.*;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.chang.omg.global.config.property.NexonCommonProperties;
import com.chang.omg.global.exception.WebClientErrorHandlingFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final NexonCommonProperties nexonCommonProperties;

    @Bean
    public WebClient webClient() {
        final HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(10))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(WebClientErrorHandlingFilter.create())
                .exchangeStrategies(getExchangeStrategies())
                .baseUrl(nexonCommonProperties.getBaseUrl())
                .build();
    }

    private ExchangeStrategies getExchangeStrategies() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(SNAKE_CASE)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, APPLICATION_JSON)))
                .build();
    }
}
