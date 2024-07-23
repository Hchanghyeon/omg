package com.chang.omg.global.converter;

import com.chang.omg.domain.game.domain.GameType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public final class GameTypeAttributeConverter implements AttributeConverter<GameType, String> {

    @Override
    public String convertToDatabaseColumn(final GameType gameType) {
        return gameType.getName();
    }

    @Override
    public GameType convertToEntityAttribute(final String gameName) {
        return GameType.from(gameName);
    }
}
