package com.chang.omg.domain.game.service.api.kartrider.dto;

import java.util.List;

public record UserTitleEquipment(List<TitleEquipment> titleEquipment) {

    record TitleEquipment(String titleName) {

    }
}
