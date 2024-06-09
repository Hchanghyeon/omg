package com.chang.omg.infrastructure.api.maplestorym.dto;

import java.util.List;

public record CharacterStat(List<Stat> stat) {

    public record Stat(String statName, String statValue) {

    }
}
