package com.chang.omg.infrastructure.api.kartrider;

import com.chang.omg.infrastructure.api.kartrider.dto.User;
import com.chang.omg.infrastructure.api.kartrider.dto.UserBasic;
import com.chang.omg.infrastructure.api.kartrider.dto.UserTitleEquipment;

public interface KartRiderApi {

    User getUserOuid(final String userName);

    UserBasic getUserBasic(final String ouid);

    UserTitleEquipment getUserTitleEquipment(final String ouid);
}
