package com.chang.omg.domain.game.service.api.maplestorym.dto;

import java.util.List;

public record CharacterStat(List<Stat> stat) {

    public record Stat(String statName, String statValue) {

    }
}
