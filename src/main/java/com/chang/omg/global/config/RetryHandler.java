package com.chang.omg.global.config;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RetryHandler implements RetryListener {

    @Override
    public <T, E extends Throwable> boolean open(final RetryContext context, final RetryCallback<T, E> callback) {
        log.warn("Retry attempt started: {}", context.getRetryCount());
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(final RetryContext context, final RetryCallback<T, E> callback,
            final Throwable throwable) {
        log.info("Retry attempt finished: {}", context.getRetryCount());
    }

    @Override
    public <T, E extends Throwable> void onSuccess(final RetryContext context, final RetryCallback<T, E> callback,
            final T result) {
        log.info("Retry attempt completed: {}", context.getRetryCount());
    }

    @Override
    public <T, E extends Throwable> void onError(final RetryContext context, final RetryCallback<T, E> callback,
            final Throwable throwable) {
        log.error("Retry attempt failed: {}", context.getRetryCount(), throwable);
    }
}
