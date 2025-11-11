package com.meeting.reservation.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import com.meeting.reservation.domain.reservation.vo.TimeSlot;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {

    @Test
    void 예약을_생성한다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1L));

        // when
        Reservation actual = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        );

        // then
        assertThat(actual).isInstanceOf(Reservation.class);
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
        LocalDateTime startTime = endTime.plusSeconds(1L);

        // when & then
        assertThatThrownBy(() -> TimeSlot.create(startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시작 시간은 예약 종료 시간보다 이전이어야 합니다.");
    }

    @Test
    void 예약끼리_시간이_겹치는지_확인한다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1L));

        Reservation first = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        );
        Reservation second = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        );

        // when
        boolean actual = first.overlapTime(second);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 유효한_예약_ID를_초기화한다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1L));
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        );

        // when
        Reservation actual = reservation.withAssignedId(1L);

        // then
        assertThat(actual.getId().getValue()).isEqualTo(1L);
    }

    @ParameterizedTest(name = "{0} 일 때 생성할 수 없다.")
    @ValueSource(longs = {0L, -1L})
    void 유효하지_않은_예약_ID는_초기화할_수_없다(Long id) {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1L));
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        );

        // when & then
        assertThatThrownBy(() -> reservation.withAssignedId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 ID는 양수여야 합니다.");
    }

    @Test
    void 비밀번호가_일치하는지_확인한다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1L));
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        );

        // when
        boolean actual = reservation.matchPassword("1234");

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 예약_ID가_일치하는지_확인한다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1L));
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        ).withAssignedId(1L);

        // when
        boolean actual = reservation.isEqualId(1L);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 회의실을_사용했는지_확인한다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusSeconds(1L));
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        ).withAssignedId(1L);

        // when
        boolean actual = reservation.afterStartTime(LocalDateTime.now().plusDays(1L));

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 일_단위로_예약을_이동시킨다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(
                LocalDateTime.of(2025, 11, 11, 10, 0),
                LocalDateTime.of(2025, 11, 11, 12, 0)
        );
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        ).withAssignedId(1L);

        // when
        Reservation actual = reservation.shift(ReservationFrequency.DAILY);

        // then
        assertAll(
                () -> assertThat(actual.getTimeSlot().getStartTime()).isEqualTo(LocalDateTime.of(2025, 11, 12, 10, 0)),
                () -> assertThat(actual.getTimeSlot().getEndTime()).isEqualTo(LocalDateTime.of(2025, 11, 12, 12, 0))
        );
    }

    @Test
    void 주_단위로_예약을_이동시킨다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(
                LocalDateTime.of(2025, 11, 11, 10, 0),
                LocalDateTime.of(2025, 11, 11, 12, 0)
        );
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        ).withAssignedId(1L);

        // when
        Reservation actual = reservation.shift(ReservationFrequency.WEEKLY);

        // then
        assertAll(
                () -> assertThat(actual.getTimeSlot().getStartTime()).isEqualTo(LocalDateTime.of(2025, 11, 18, 10, 0)),
                () -> assertThat(actual.getTimeSlot().getEndTime()).isEqualTo(LocalDateTime.of(2025, 11, 18, 12, 0))
        );
    }

    @Test
    void 월_단위로_예약을_이동시킨다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(
                LocalDateTime.of(2025, 1, 15, 10, 0),
                LocalDateTime.of(2025, 1, 15, 12, 0)
        );
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        ).withAssignedId(1L);

        // when
        Reservation actual = reservation.shift(ReservationFrequency.MONTHLY);

        // then
        assertAll(
                () -> assertThat(actual.getTimeSlot().getStartTime()).isEqualTo(LocalDateTime.of(2025, 2, 15, 10, 0)),
                () -> assertThat(actual.getTimeSlot().getEndTime()).isEqualTo(LocalDateTime.of(2025, 2, 15, 12, 0))
        );
    }

    @Test
    void 년_단위로_예약을_이동시킨다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(
                LocalDateTime.of(2025, 11, 11, 10, 0),
                LocalDateTime.of(2025, 11, 11, 12, 0)
        );
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        ).withAssignedId(1L);

        // when
        Reservation actual = reservation.shift(ReservationFrequency.YEARLY);

        // then
        assertAll(
                () -> assertThat(actual.getTimeSlot().getStartTime()).isEqualTo(LocalDateTime.of(2026, 11, 11, 10, 0)),
                () -> assertThat(actual.getTimeSlot().getEndTime()).isEqualTo(LocalDateTime.of(2026, 11, 11, 12, 0))
        );
    }

    @Test
    void 예약_이동_후_ID를_제외한_다른_속성들은_유지된다() {
        // given
        MeetingRoomId meetingRoomId = MeetingRoomId.create(1L);
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = TimeSlot.create(
                LocalDateTime.of(2025, 11, 11, 10, 0),
                LocalDateTime.of(2025, 11, 11, 12, 0)
        );
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                timeSlot,
                5,
                organizer
        ).withAssignedId(1L);

        // when
        Reservation actual = reservation.shift(ReservationFrequency.DAILY);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isSameAs(ReservationId.EMPTY_RESERVATION_ID),
                () -> assertThat(actual.getMeetingRoomId().getValue()).isEqualTo(1L),
                () -> assertThat(actual.getAttendeeCount()).isEqualTo(5),
                () -> assertThat(actual.getOrganizer()).isEqualTo(organizer)
        );
    }
}
