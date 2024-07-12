package com.chang.omg.infrastructure.api.kartrider;

import com.chang.omg.infrastructure.api.kartrider.dto.User;
import com.chang.omg.infrastructure.api.kartrider.dto.UserBasic;
import com.chang.omg.infrastructure.api.kartrider.dto.UserTitleEquipment;

import reactor.core.publisher.Mono;

public interface KartRiderApi {

    User getUserOuid(final String userName);

    Mono<UserBasic> getUserBasic(final String ouid);

    Mono<UserTitleEquipment> getUserTitleEquipment(final String ouid);
}
