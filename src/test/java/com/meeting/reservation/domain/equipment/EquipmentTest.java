package com.meeting.reservation.domain.equipment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EquipmentTest {

    @Test
    void 비품을_생성한다() {
        // when
        Equipment actual = Equipment.create("프로젝터", 5);

        // then
        assertThat(actual).isInstanceOf(Equipment.class);
    }

    @ParameterizedTest(name = "{0} 일 때 생성할 수 없다.")
    @NullAndEmptySource
    void 비품_이름은_비어_있을_수_없다(String name) {
        // given & when & then
        assertThatThrownBy(() -> Equipment.create(name, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비품 이름은 비어 있을 수 없습니다.");
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @ValueSource(ints = {0, -1, -100})
    void 비품_수량은_양수여야_한다(int quantity) {
        // given & when & then
        assertThatThrownBy(() -> Equipment.create("프로젝터", quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비품 수량은 양수여야 합니다,");
    }

    @Test
    void 요청_수량이_보유_수량보다_적으면_비품_제공이_가능하다() {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10);

        // when
        boolean actual = equipment.canProvide(5);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 요청_수량이_보유_수량과_같으면_비품_제공이_가능하다() {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10);

        // when
        boolean actual = equipment.canProvide(10);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 요청_수량이_보유_수량보다_많으면_비품_제공이_불가능하다() {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10);

        // when
        boolean actual = equipment.canProvide(11);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 유효한_비품_ID를_초기화한다() {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10);

        // when
        Equipment actual = equipment.withAssignedId(1L);

        // then
        assertThat(actual.getId().getId()).isEqualTo(1L);
    }

    @ParameterizedTest(name = "{0}일 때 초기화할 수 없다.")
    @ValueSource(longs = {0L, -1L, -100L})
    void 유효하지_않은_비품_ID는_초기화할_수_없다(Long id) {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10);

        // when & then
        assertThatThrownBy(() -> equipment.withAssignedId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비품 ID는 양수여야 합니다.");
    }

    @Test
    void 비품_ID_초기화_후_다른_속성들은_유지된다() {
        // given
        Equipment equipment = Equipment.create("프로젝터", 10);

        // when
        Equipment actual = equipment.withAssignedId(1L);

        // then
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo("프로젝터"),
                () -> assertThat(actual.getQuantity()).isEqualTo(10)
        );
    }
}
