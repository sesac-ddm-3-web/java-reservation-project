package com.meeting.reservation.domain.equipment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EquipmentsTest {

    @Test
    void 비품_목록을_생성한다() {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10)
                                       .withAssignedId(1L);
        Map<EquipmentId, Equipment> equipmentMap = new HashMap<>();
        equipmentMap.put(equipment.getId(), equipment);

        // when
        Equipments actual = Equipments.create(equipmentMap);

        // then
        assertThat(actual).isInstanceOf(Equipments.class);
    }

    @Test
    void 비품_ID로_비품을_찾는다() {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10)
                                       .withAssignedId(1L);
        Map<EquipmentId, Equipment> equipmentMap = new HashMap<>();
        equipmentMap.put(equipment.getId(), equipment);
        Equipments equipments = Equipments.create(equipmentMap);

        // when
        Equipment actual = equipments.find(EquipmentId.create(1L));

        // then
        assertThat(actual).isEqualTo(equipment);
    }

    @Test
    void 존재하지_않는_비품_ID로는_조회할_수_없다() {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10)
                                       .withAssignedId(1L);
        Map<EquipmentId, Equipment> equipmentMap = new HashMap<>();
        equipmentMap.put(equipment.getId(), equipment);
        Equipments equipments = Equipments.create(equipmentMap);

        // when & then
        assertThatThrownBy(() -> equipments.find(EquipmentId.create(999L)))
                .isInstanceOf(Equipments.EquipmentNotFoundException.class)
                .hasMessage("지정한 비품을 찾을 수 없습니다.");
    }

    @Test
    void 모든_비품을_조회한다() {
        // given
        Equipment equipment1 = Equipment.create("프로젝터", 10)
                                        .withAssignedId(1L);
        Equipment equipment2 = Equipment.create("화이트보드", 5)
                                        .withAssignedId(2L);
        Map<EquipmentId, Equipment> equipmentMap = new HashMap<>();
        equipmentMap.put(equipment1.getId(), equipment1);
        equipmentMap.put(equipment2.getId(), equipment2);
        Equipments equipments = Equipments.create(equipmentMap);

        // when
        List<Equipment> actual = equipments.getEquipments();

        // then
        assertThat(actual).hasSize(2);
    }

    @Test
    void 비어_있는_비품_목록을_조회한다() {
        // given
        Equipments equipments = Equipments.create(new HashMap<>());

        // when
        List<Equipment> actual = equipments.getEquipments();

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    void 비품_목록은_불변_리스트로_반환된다() {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10)
                                       .withAssignedId(1L);
        Map<EquipmentId, Equipment> equipmentMap = new HashMap<>();
        equipmentMap.put(equipment.getId(), equipment);
        Equipments equipments = Equipments.create(equipmentMap);

        // when
        List<Equipment> actual = equipments.getEquipments();

        // then
        assertThatThrownBy(() -> actual.add(Equipment.create("화이트보드", 5)))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
