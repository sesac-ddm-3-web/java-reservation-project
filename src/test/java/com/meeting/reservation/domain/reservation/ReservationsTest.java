package com.meeting.reservation.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.meeting.reservation.domain.reservation.vo.EquipmentUsages;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import com.meeting.reservation.domain.reservation.vo.TimeSlot;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationsTest {

    @Test
    void 예약_목록을_생성한다() {
        // given
        Organizer organizer = createOrganizer("예약자", "010-1234-5678", "1234");
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot,
                5,
                organizer,
                EquipmentUsages.create(Collections.emptyMap())
        );

        // when
        Reservations actual = Reservations.create(MeetingRoomId.create(1L), List.of(reservation));

        // then
        assertThat(actual).isInstanceOf(Reservations.class);
    }

    @Test
    void 유효한_예약을_추가할_수_있는지_검증한다() {
        // given
        Organizer organizer1 = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot1 = createTimeSlot();
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot1,
                5,
                organizer1,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Organizer organizer2 = createOrganizer("예약자2", "010-1234-5678", "4321");
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusSeconds(1L)
        );
        Reservation targetReservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot2,
                5,
                organizer2,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(2L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(reservation));

        // when & then
        assertDoesNotThrow(() -> reservations.validateReserve(targetReservation));
    }

    @Test
    void 동등한_예약은_추가할_수_없다() {
        // given
        Organizer organizer1 = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot1 = createTimeSlot();
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot1,
                5,
                organizer1,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Organizer organizer2 = createOrganizer("예약자2", "010-1234-5678", "4321");
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusSeconds(1L)
        );
        Reservation targetReservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot2,
                5,
                organizer2,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(reservation));

        // when & then
        assertThatThrownBy(() -> reservations.validateReserve(targetReservation))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 예약입니다.");
    }

    @Test
    void 시간이_중복된_에약은_추가할_수_없다() {
        // given
        Organizer organizer1 = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot,
                5,
                organizer1,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Organizer organizer2 = createOrganizer("예약자2", "010-1234-5678", "4321");
        Reservation targetReservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot,
                5,
                organizer2,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(2L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(reservation));

        // when & then
        assertThatThrownBy(() -> reservations.validateReserve(targetReservation))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 시간이 겹치는 예약이 존재합니다.");
    }

    @Test
    void 예약을_취소할_수_있는지_검증한다() {
        // given
        Organizer organizer = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot,
                5,
                organizer,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(reservation));

        // when & then
        assertDoesNotThrow(
                () -> reservations.validateCancel(1L, "1234", LocalDateTime.now().minusDays(1L))
        );
    }

    @Test
    void 지정한_예약_ID가_없다면_예약을_취소할_수_없다() {
        // given
        Organizer organizer = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot,
                5,
                organizer,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(reservation));

        // when & then
        assertThatThrownBy(() -> reservations.validateCancel(-999L, "1234", LocalDateTime.now().minusDays(1L)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 ID에 해당하는 예약을 찾을 수 없습니다.");
    }

    @Test
    void 이미_회의실을_사용하거나_사용했다면_예약을_취소할_수_없다() {
        // given
        Organizer organizer = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot,
                5,
                organizer,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(reservation));

        // when & then
        assertThatThrownBy(() -> reservations.validateCancel(1L, "1234", LocalDateTime.now().plusDays(1L)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지금 회의실을 사용하고 있거나 이미 사용했습니다.");
    }

    @Test
    void 회의실_예약_시_입력한_비밀번호가_다르다면_예약을_취소할_수_없다() {
        // given
        Organizer organizer = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot,
                5,
                organizer,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(reservation));

        // when & then
        assertThatThrownBy(() -> reservations.validateCancel(1L, "54321", LocalDateTime.now().minusDays(1L)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 반복_예약이_모두_유효한지_검증한다() {
        // given
        Organizer organizer1 = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot1 = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusHours(1L));
        Reservation existingReservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot1,
                5,
                organizer1,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Organizer organizer2 = createOrganizer("예약자2", "010-1234-5678", "4321");
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusHours(1L)
        );
        TimeSlot timeSlot3 = TimeSlot.create(
                LocalDateTime.now().plusDays(2L),
                LocalDateTime.now().plusDays(2L).plusHours(1L)
        );

        Reservation targetReservation1 = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot2,
                5,
                organizer2,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(2L);
        Reservation targetReservation2 = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot3,
                5,
                organizer2,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(3L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(existingReservation));
        List<Reservation> targetReservations = List.of(targetReservation1, targetReservation2);

        // when & then
        assertDoesNotThrow(() -> reservations.validateRepeatReserve(targetReservations));
    }

    @Test
    void 반복_예약_중_하나가_기존_예약과_동등하면_반복_예외_전체를_취소한다() {
        // given
        Organizer organizer1 = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot1 = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusHours(1L));
        Reservation existingReservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot1,
                5,
                organizer1,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Organizer organizer2 = createOrganizer("예약자2", "010-1234-5678", "4321");
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusHours(1L)
        );

        Reservation targetReservation1 = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot2,
                5,
                organizer2,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(2L);
        Reservation targetReservation2 = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot1,
                5,
                organizer2,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(existingReservation));
        List<Reservation> targetReservations = List.of(targetReservation1, targetReservation2);

        // when & then
        assertThatThrownBy(() -> reservations.validateRepeatReserve(targetReservations))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 예약입니다.");
    }

    @Test
    void 반복_예약_중_하나가_기존_예약과_시간이_겹치면_반복_예약_전체를_취소한다() {
        // given
        Organizer organizer1 = createOrganizer("예약자1", "010-5678-1234", "1234");
        TimeSlot timeSlot1 = TimeSlot.create(LocalDateTime.now(), LocalDateTime.now().plusHours(2L));
        Reservation existingReservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot1,
                5,
                organizer1,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(1L);

        Organizer organizer2 = createOrganizer("예약자2", "010-1234-5678", "4321");
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusHours(1L)
        );
        TimeSlot timeSlot3 = TimeSlot.create(
                LocalDateTime.now().plusMinutes(30L),
                LocalDateTime.now().plusMinutes(30L).plusHours(1L)
        );

        Reservation targetReservation1 = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot2,
                5,
                organizer2,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(2L);
        Reservation targetReservation2 = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot3,
                5,
                organizer2,
                EquipmentUsages.create(Collections.emptyMap())
        ).withAssignedId(3L);

        Reservations reservations = Reservations.create(MeetingRoomId.create(1L), List.of(existingReservation));
        List<Reservation> targetReservations = List.of(targetReservation1, targetReservation2);

        // when & then
        assertThatThrownBy(() -> reservations.validateRepeatReserve(targetReservations))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 시간이 겹치는 예약이 존재합니다.");
    }

    private Organizer createOrganizer(String name, String phoneNumber, String password) {
        return Organizer.create(name, phoneNumber, password);
    }

    private TimeSlot createTimeSlot() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);
        return TimeSlot.create(startTime, endTime);
    }
}
