package com.chang.omg.infrastructure.api.maplestorym.dto;

import java.util.List;

public record CharacterItemEquipment(List<ItemEquipment> itemEquipment) {

    public record ItemEquipment(String itemName, String itemEquipmentPageName, String itemEquipmentSlotName) {

    }
}
