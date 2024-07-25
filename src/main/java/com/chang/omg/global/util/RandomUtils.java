package com.chang.omg.global.util;

import java.util.concurrent.ThreadLocalRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomUtils {

    public static int createAuthCode() {
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }
}
