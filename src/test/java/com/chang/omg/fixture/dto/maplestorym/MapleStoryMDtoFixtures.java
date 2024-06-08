package com.chang.omg.fixture.dto.maplestorym;

import com.chang.omg.infrastructure.api.maplestorym.dto.Character;
import com.chang.omg.infrastructure.api.maplestorym.dto.CharacterBasic;

public final class MapleStoryMDtoFixtures {

    public static Character characterBuild() {
        return new Character("1eab45c6s2c4d56e1d5c7e");
    }

    public static CharacterBasic characterBasicBuild() {
        return new CharacterBasic(
                "황창구리",
                "스카니아",
                "2024-01-25T08:21:47.552Z",
                "2024-03-03T00:03:59.704Z",
                "2024-03-03T03:04:03.98Z",
                "소울마스터",
                "Male",
                "208658892",
                "150"
        );
    }
}
