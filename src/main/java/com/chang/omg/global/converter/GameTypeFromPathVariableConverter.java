package com.chang.omg.global.converter;

import org.springframework.core.convert.converter.Converter;

import com.chang.omg.domain.game.domain.GameType;

public final class GameTypeFromPathVariableConverter implements Converter<String, GameType> {

    @Override
    public GameType convert(final String gameName) {
        return GameType.valueOf(gameName.toUpperCase());
    }
}
