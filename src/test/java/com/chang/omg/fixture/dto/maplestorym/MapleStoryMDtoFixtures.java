package com.chang.omg.fixture.dto.maplestorym;

import java.util.ArrayList;
import java.util.List;

import com.chang.omg.domain.game.service.api.maplestorym.dto.Character;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterBasic;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterGuild;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterItemEquipment;
import com.chang.omg.domain.game.service.api.maplestorym.dto.CharacterStat;

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

    public static CharacterItemEquipment characterItemEquipmentBuild() {
        return new CharacterItemEquipment(itemEquipmentBuild(3));
    }

    private static List<CharacterItemEquipment.ItemEquipment> itemEquipmentBuild(final int count) {
        final List<CharacterItemEquipment.ItemEquipment> itemEquipments = new ArrayList<>(count);

        for (int i = 1; i <= count; i++) {
            final CharacterItemEquipment.ItemEquipment itemEquipment = new CharacterItemEquipment.ItemEquipment(
                    "아이템" + i,
                    "아이템페이지" + i,
                    "아이템슬롯" + i
            );

            itemEquipments.add(itemEquipment);
        }

        return itemEquipments;
    }

    public static CharacterStat characterStat() {
        return new CharacterStat(statBuild());
    }

    private static List<CharacterStat.Stat> statBuild() {
        final List<CharacterStat.Stat> stats = new ArrayList<>();

        stats.add(new CharacterStat.Stat("전투력", "62899"));
        stats.add(new CharacterStat.Stat("물리 공격력", "7383"));
        stats.add(new CharacterStat.Stat("마법 공격력", "5932"));
        stats.add(new CharacterStat.Stat("물리 방어력", "12336"));
        stats.add(new CharacterStat.Stat("마법 방어력", "12222"));
        stats.add(new CharacterStat.Stat("HP", "142845"));
        stats.add(new CharacterStat.Stat("MP", "34236"));

        return stats;
    }

    public static CharacterGuild characterGuildBuild() {
        return new CharacterGuild(null);
    }
}
