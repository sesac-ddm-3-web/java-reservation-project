package com.meeting.reservation.domain.equipment.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EquipmentIdTest {

    @Test
    void 비품_ID를_생성한다() {
        // given & when
        EquipmentId actual = EquipmentId.create(1L);

        // then
        assertThat(actual.getId()).isEqualTo(1L);
    }

    @Test
    void 비품_ID가_null이면_예외가_발생한다() {
        // given & when & then
        assertThatThrownBy(() -> EquipmentId.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비품 ID는 양수여야 합니다.");
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @ValueSource(longs = {0L, -1L, -100L})
    void 비품_ID가_양수가_아니면_예외가_발생한다(Long value) {
        // given & when & then
        assertThatThrownBy(() -> EquipmentId.create(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비품 ID는 양수여야 합니다.");
    }

    @Test
    void 같은_값을_가진_비품_ID는_동등하다() {
        // given
        EquipmentId equipmentId1 = EquipmentId.create(1L);
        EquipmentId equipmentId2 = EquipmentId.create(1L);

        // when & then
        assertThat(equipmentId1).isEqualTo(equipmentId2);
    }

    @Test
    void 다른_값을_가진_비품_ID는_동등하지_않다() {
        // given
        EquipmentId equipmentId1 = EquipmentId.create(1L);
        EquipmentId equipmentId2 = EquipmentId.create(2L);

        // when & then
        assertThat(equipmentId1).isNotEqualTo(equipmentId2);
    }
}
