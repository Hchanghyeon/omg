package com.chang.omg.domain.game.service.api.kartrider.dto;

import java.util.List;

public record User(List<OuidInfo> ouidInfo) {

    public record OuidInfo(String ouid, String racerDateCreate, String racerLevel) {

    }
}
