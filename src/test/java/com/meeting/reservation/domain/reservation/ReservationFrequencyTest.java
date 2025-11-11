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
    void DAILY는_일_단위로_날짜를_더한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 11, 11, 10, 0);
        long amount = 3L;

        // when
        LocalDateTime actual = ReservationFrequency.DAILY.addTo(dateTime, amount);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2025, 11, 14, 10, 0));
    }

    @Test
    void WEEKLY는_주_단위로_날짜를_더한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 11, 11, 10, 0);
        long amount = 2L;

        // when
        LocalDateTime actual = ReservationFrequency.WEEKLY.addTo(dateTime, amount);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2025, 11, 25, 10, 0));
    }

    @Test
    void MONTHLY는_월_단위로_날짜를_더한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 1, 15, 10, 0);
        long amount = 3L;

        // when
        LocalDateTime actual = ReservationFrequency.MONTHLY.addTo(dateTime, amount);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2025, 4, 15, 10, 0));
    }

    @Test
    void YEARLY는_년_단위로_날짜를_더한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 11, 11, 10, 0);
        long amount = 2L;

        // when
        LocalDateTime actual = ReservationFrequency.YEARLY.addTo(dateTime, amount);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2027, 11, 11, 10, 0));
    }

    @Test
    void MONTHLY는_존재하지_않는_날짜를_해당_월의_마지막_날로_조정한다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 10, 31, 10, 0);
        long amount = 1L;

        // when
        LocalDateTime actual = ReservationFrequency.MONTHLY.addTo(dateTime, amount);

        // then
        assertThat(actual).isEqualTo(LocalDateTime.of(2025, 11, 30, 10, 0));
    }
}
