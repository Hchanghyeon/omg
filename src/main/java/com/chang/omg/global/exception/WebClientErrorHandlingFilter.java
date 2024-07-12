package com.chang.omg.global.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebClientErrorHandlingFilter {

    private static final int FORBIDDEN = 403;
    private static final int BAD_REQUEST = 400;
    private static final int TOO_MANY_REQUESTS = 429;

    public static ExchangeFilterFunction create() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            final HttpStatusCode status = clientResponse.statusCode();

            if (status.is4xxClientError() || status.is5xxServerError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(createException(status, errorBody)));
            } else {
                return Mono.just(clientResponse);
            }
        });
    }

    private static Throwable createException(final HttpStatusCode status, final String errorBody) {
        if (status.is4xxClientError()) {
            return switch (status.value()) {
                case BAD_REQUEST -> new ApiException(ApiExceptionCode.API_BAD_REQUEST, errorBody);
                case FORBIDDEN -> new ApiException(ApiExceptionCode.API_FORBIDDEN, errorBody);
                case TOO_MANY_REQUESTS -> new ApiException(ApiExceptionCode.API_TOO_MANY_REQUESTS, errorBody);
                default -> new ApiException(ApiExceptionCode.API_UNKNOWN_ERROR, errorBody);
            };
        }

        return new ApiException(ApiExceptionCode.API_INTERNAL_SERVER_ERROR, errorBody);
    }
}
