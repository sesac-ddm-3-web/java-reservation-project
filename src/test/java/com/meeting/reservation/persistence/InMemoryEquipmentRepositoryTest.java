package com.meeting.reservation.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.meeting.reservation.domain.equipment.Equipments;
import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InMemoryEquipmentRepositoryTest {

    @Test
    void 미리_초기화된_비품을_가진_레포지토리를_생성한다() {
        // given
        InMemoryEquipmentRepository repository = new InMemoryEquipmentRepository();

        // when
        Equipments equipments = repository.findAll(MeetingRoomId.create(1L));

        // then
        assertAll(
                () -> assertThat(equipments.find(EquipmentId.create(1L)).getName()).isEqualTo("빔프로젝터"),
                () -> assertThat(equipments.find(EquipmentId.create(1L)).getQuantity()).isEqualTo(5),
                () -> assertThat(equipments.find(EquipmentId.create(1L)).getId().getId()).isEqualTo(1L),

                () -> assertThat(equipments.find(EquipmentId.create(2L)).getName()).isEqualTo("회의용 스피커폰"),
                () -> assertThat(equipments.find(EquipmentId.create(2L)).getQuantity()).isEqualTo(10),
                () -> assertThat(equipments.find(EquipmentId.create(2L)).getId().getId()).isEqualTo(2L),

                () -> assertThat(equipments.find(EquipmentId.create(3L)).getName()).isEqualTo("화상 회의 키트"),
                () -> assertThat(equipments.find(EquipmentId.create(3L)).getQuantity()).isEqualTo(4),
                () -> assertThat(equipments.find(EquipmentId.create(3L)).getId().getId()).isEqualTo(3L),

                () -> assertThat(equipments.find(EquipmentId.create(4L)).getName()).isEqualTo("화이트보드"),
                () -> assertThat(equipments.find(EquipmentId.create(4L)).getQuantity()).isEqualTo(8),
                () -> assertThat(equipments.find(EquipmentId.create(4L)).getId().getId()).isEqualTo(4L),

                () -> assertThat(equipments.find(EquipmentId.create(5L)).getName()).isEqualTo("노트북"),
                () -> assertThat(equipments.find(EquipmentId.create(5L)).getQuantity()).isEqualTo(20),
                () -> assertThat(equipments.find(EquipmentId.create(5L)).getId().getId()).isEqualTo(5L)
        );
    }
}
