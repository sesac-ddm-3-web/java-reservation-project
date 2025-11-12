package com.meeting.reservation.domain.reservation.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.meeting.reservation.domain.reservation.ReservationFrequency;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TimeSlotTest {

    @Test
    void 예약_시간을_생성한다() {
        // given
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);

        // when
        TimeSlot actual = TimeSlot.create(startTime, endTime);

        // then
        assertThat(actual).isInstanceOf(TimeSlot.class);
    }

    @Test
    void 예약_시간_정보가_있어야_한다() {
        // when & then
        assertThatThrownBy(() -> TimeSlot.create(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간 정보는 비어 있을 수 없습니다.");
    }

    @Test
    void 예약_시작_시간은_예약_종료_시간보다_앞서야_한다() {
        // given
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.plusHours(1L);

        // when & then
        assertThatThrownBy(() -> TimeSlot.create(startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시작 시간은 예약 종료 시간보다 이전이어야 합니다.");
    }

    @Test
    void 예약_시간끼리_겹치는지_확인한다() {
        // given
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2L);
        TimeSlot first = TimeSlot.create(startTime, endTime);

        LocalDateTime otherStartTime = startTime.plusHours(1L);
        LocalDateTime otherEndTime = otherStartTime.plusHours(2L);
        TimeSlot second = TimeSlot.create(otherStartTime, otherEndTime);

        // when
        boolean actual = first.overlapTime(second);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 예약_시간끼리_겹치지_않는지_확인한다() {
        // given
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);
        TimeSlot first = TimeSlot.create(startTime, endTime);

        LocalDateTime otherStartTime = endTime.plusHours(1L);
        LocalDateTime otherEndTime = otherStartTime.plusHours(1L);
        TimeSlot second = TimeSlot.create(otherStartTime, otherEndTime);

        // when
        boolean actual = first.overlapTime(second);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 예약_시작_시간이_지났는지_확인한다() {
        // given
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);
        TimeSlot timeSlot = TimeSlot.create(startTime, endTime);

        // when
        boolean actual = timeSlot.afterStartTime(LocalDateTime.now().plusDays(1L));

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 일_단위로_예약_시간을_이동시킨다() {
        // given
        LocalDateTime startTime = LocalDateTime.of(2025, 11, 11, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 11, 11, 12, 0);
        TimeSlot timeSlot = TimeSlot.create(startTime, endTime);

        // when
        TimeSlot actual = timeSlot.shiftBy(ReservationFrequency.DAILY);

        // then
        assertThat(actual.getStartTime()).isEqualTo(LocalDateTime.of(2025, 11, 12, 10, 0));
        assertThat(actual.getEndTime()).isEqualTo(LocalDateTime.of(2025, 11, 12, 12, 0));
    }

    @Test
    void 주_단위로_예약_시간을_이동시킨다() {
        // given
        LocalDateTime startTime = LocalDateTime.of(2025, 11, 11, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 11, 11, 12, 0);
        TimeSlot timeSlot = TimeSlot.create(startTime, endTime);

        // when
        TimeSlot actual = timeSlot.shiftBy(ReservationFrequency.WEEKLY);

        // then
        assertThat(actual.getStartTime()).isEqualTo(LocalDateTime.of(2025, 11, 18, 10, 0));
        assertThat(actual.getEndTime()).isEqualTo(LocalDateTime.of(2025, 11, 18, 12, 0));
    }

    @Test
    void 월_단위로_예약_시간을_이동시킨다() {
        // given
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 15, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 15, 12, 0);
        TimeSlot timeSlot = TimeSlot.create(startTime, endTime);

        // when
        TimeSlot actual = timeSlot.shiftBy(ReservationFrequency.MONTHLY);

        // then
        assertThat(actual.getStartTime()).isEqualTo(LocalDateTime.of(2025, 2, 15, 10, 0));
        assertThat(actual.getEndTime()).isEqualTo(LocalDateTime.of(2025, 2, 15, 12, 0));
    }

    @Test
    void 년_단위로_예약_시간을_이동시킨다() {
        // given
        LocalDateTime startTime = LocalDateTime.of(2025, 11, 11, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 11, 11, 12, 0);
        TimeSlot timeSlot = TimeSlot.create(startTime, endTime);

        // when
        TimeSlot actual = timeSlot.shiftBy(ReservationFrequency.YEARLY);

        // then
        assertThat(actual.getStartTime()).isEqualTo(LocalDateTime.of(2026, 11, 11, 10, 0));
        assertThat(actual.getEndTime()).isEqualTo(LocalDateTime.of(2026, 11, 11, 12, 0));
    }
}
