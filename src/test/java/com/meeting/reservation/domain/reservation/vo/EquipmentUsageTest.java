package com.meeting.reservation.domain.reservation.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EquipmentUsageTest {

    @Test
    void 비품_사용_정보를_생성한다() {
        // given
        EquipmentId equipmentId = EquipmentId.create(1L);

        // when
        EquipmentUsage actual = EquipmentUsage.create(equipmentId, "프로젝터", 5);

        // then
        assertAll(
                () -> assertThat(actual.getEquipmentId()).isEqualTo(equipmentId),
                () -> assertThat(actual.getQuantity()).isEqualTo(5)
        );
    }

    @Test
    void 비품_ID가_없으면_비품_사용_정보를_생성할_수_없다() {
        // when & then
        assertThatThrownBy(() -> EquipmentUsage.create(null, "프로젝터", 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비품 ID는 비어 있을 수 없습니다.");
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @ValueSource(ints = {0, -1})
    void 비품_수량이_양수가_비품_사용_정보를_생성할_수_없다(int quantity) {
        // when & then
        assertThatThrownBy(() -> EquipmentUsage.create(EquipmentId.create(1L), "프로젝터", quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비품 수량은 양수여야 합니다.");
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @NullAndEmptySource
    void 비품_이름이_없다면_비품_사용_정보를_생성할_수_없다(String name) {
        // when & then
        assertThatThrownBy(() -> EquipmentUsage.create(EquipmentId.create(1L), name, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비품 이름은 비어 있을 수 없습니다.");
    }
}
