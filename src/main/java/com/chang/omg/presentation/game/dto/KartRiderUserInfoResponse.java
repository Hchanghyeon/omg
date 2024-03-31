package com.chang.omg.presentation.game.dto;

import com.chang.omg.infrastructure.api.kartrider.dto.UserBasic;
import com.chang.omg.infrastructure.api.kartrider.dto.UserTitleEquipment;

public record KartRiderUserInfoResponse(UserBasic userBasic, UserTitleEquipment userTitleEquipment) {

    public static KartRiderUserInfoResponse of(
            final UserBasic userBasic,
            final UserTitleEquipment userTitleEquipment
    ) {
        return new KartRiderUserInfoResponse(userBasic, userTitleEquipment);
    }
}
