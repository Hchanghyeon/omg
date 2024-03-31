package com.chang.omg.infrastructure.api.kartrider.dto;

import java.util.List;

public record UserTitleEquipment(List<TitleEquipment> titleEquipment) {

    record TitleEquipment(String titleName) {

    }
}
