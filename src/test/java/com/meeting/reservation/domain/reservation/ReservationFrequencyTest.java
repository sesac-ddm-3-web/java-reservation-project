package com.meeting.reservation.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationFrequencyTest {

    @Test
    void 예약_주기를_이름으로_찾는다() {
        // given
        String name = "DAILY";

        // when
        ReservationFrequency actual = ReservationFrequency.find(name);

        // then
        assertThat(actual).isEqualTo(ReservationFrequency.DAILY);
    }

    @Test
    void 예약_주기를_대소문자_구분_없이_찾는다() {
        // given
        String name = "weekly";

        // when
        ReservationFrequency actual = ReservationFrequency.find(name);

        // then
        assertThat(actual).isEqualTo(ReservationFrequency.WEEKLY);
    }

    @Test
    void 존재하지_않는_이름으로_예약_주기를_찾을_수_없다() {
        // given
        String name = "INVALID";

        // when & then
        assertThatThrownBy(() -> ReservationFrequency.find(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 예약 주기를 찾을 수 없습니다.");
    }

    @Test
    void DAILY는_기존_날짜에_1일을_더한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 11, 11, 10, 0);

        // when
        LocalDateTime actual = ReservationFrequency.DAILY.addTo(dateTime);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2025, 11, 12, 10, 0));
    }

    @Test
    void WEEKLY는_기존_날짜에_1주를_더한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 11, 11, 10, 0);

        // when
        LocalDateTime actual = ReservationFrequency.WEEKLY.addTo(dateTime);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2025, 11, 18, 10, 0));
    }

    @Test
    void MONTHLY는_기존_날짜에_1달을_더한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 1, 15, 10, 0);

        // when
        LocalDateTime actual = ReservationFrequency.MONTHLY.addTo(dateTime);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2025, 2, 15, 10, 0));
    }

    @Test
    void YEARLY는_기존_날짜에_1년을_더한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 11, 11, 10, 0);

        // when
        LocalDateTime actual = ReservationFrequency.YEARLY.addTo(dateTime);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2026, 11, 11, 10, 0));
    }

    @Test
    void MONTHLY는_존재하지_않는_날짜를_해당_월의_마지막_날로_조정한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 10, 31, 10, 0);

        // when
        LocalDateTime actual = ReservationFrequency.MONTHLY.addTo(dateTime);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2025, 11, 30, 10, 0));
    }
}
