package com.meeting.reservation.domain.reservation.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationIdTest {

    @Test
    void 예약_ID를_생성한다() {
        // when
        ReservationId actual = ReservationId.create(1L);

        // then
        assertAll(
                () -> assertThat(actual).isInstanceOf(ReservationId.class),
                () -> assertThat(actual.getValue()).isEqualTo(1L)
        );
    }

    @ParameterizedTest(name = "{0} 일 때 생성할 수 없다.")
    @ValueSource(longs = {0L, -1L})
    void 회의실_ID는_양수여야_한다(Long value) {
        // when & then
        assertThatThrownBy(() -> ReservationId.create(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 ID는 양수여야 합니다.");
    }
}
