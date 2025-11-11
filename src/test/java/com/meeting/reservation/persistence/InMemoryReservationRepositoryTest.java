package com.meeting.reservation.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.ReservationFactory;
import com.meeting.reservation.domain.reservation.Reservations;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.TimeSlot;
import com.meeting.reservation.domain.room.MeetingRoom;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import com.meeting.reservation.domain.room.vo.MeetingRoomLocation;
import com.meeting.reservation.persistence.InMemoryReservationRepository.MeetingRoomNotFoundException;
import com.meeting.reservation.persistence.InMemoryReservationRepository.ReservationNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InMemoryReservationRepositoryTest {

    private final InMemoryReservationRepository repository = new InMemoryReservationRepository();
    private final ReservationFactory reservationFactory = new ReservationFactory(repository);

    @Test
    void 예약을_저장한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = reservationFactory.create(meetingRoom, organizer, timeSlot, 5);

        // when
        Reservation actual = repository.save(reservation);

        // then
        assertThat(actual.getId().getValue()).isEqualTo(1L);
    }

    @Test
    void 예약을_저장할_때마다_ID가_자동으로_증가한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot1 = createTimeSlot();
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusHours(1L)
        );
        Reservation reservation1 = reservationFactory.create(meetingRoom, organizer, timeSlot1, 5);
        Reservation reservation2 = reservationFactory.create(meetingRoom, organizer, timeSlot2, 5);

        // when
        Reservation saved1 = repository.save(reservation1);
        Reservation saved2 = repository.save(reservation2);

        // then
        assertAll(
                () -> assertThat(saved1.getId().getValue()).isEqualTo(1L),
                () -> assertThat(saved2.getId().getValue()).isEqualTo(2L)
        );
    }

    @Test
    void 여러_예약을_한_번에_저장한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot1 = createTimeSlot();
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusHours(1L)
        );
        TimeSlot timeSlot3 = TimeSlot.create(
                LocalDateTime.now().plusDays(2L),
                LocalDateTime.now().plusDays(2L).plusHours(1L)
        );
        Reservation reservation1 = reservationFactory.create(meetingRoom, organizer, timeSlot1, 5);
        Reservation reservation2 = reservationFactory.create(meetingRoom, organizer, timeSlot2, 5);
        Reservation reservation3 = reservationFactory.create(meetingRoom, organizer, timeSlot3, 5);

        // when
        List<Reservation> actual = repository.saveAll(List.of(reservation1, reservation2, reservation3));

        // then
        assertThat(actual).hasSize(3);
    }

    @Test
    void 여러_예약을_저장할_때_각각_ID가_증가한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot1 = createTimeSlot();
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusHours(1L)
        );
        Reservation reservation1 = reservationFactory.create(meetingRoom, organizer, timeSlot1, 5);
        Reservation reservation2 = reservationFactory.create(meetingRoom, organizer, timeSlot2, 5);

        // when
        List<Reservation> actual = repository.saveAll(List.of(reservation1, reservation2));

        // then
        assertAll(
                () -> assertThat(actual.get(0).getId().getValue()).isEqualTo(1L),
                () -> assertThat(actual.get(1).getId().getValue()).isEqualTo(2L)
        );
    }

    @Test
    void 예약을_삭제한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = reservationFactory.create(meetingRoom, organizer, timeSlot, 5);
        Reservation saved = repository.save(reservation);

        // when
        repository.delete(MeetingRoomId.create(1L), saved.getId().getValue());
        Reservations actual = repository.findAll(MeetingRoomId.create(1L));

        // then
        assertThat(actual.getReservations()).isEmpty();
    }

    @Test
    void 존재하지_않는_회의실의_예약은_삭제할_수_없다() {
        // given & when & then
        assertThatThrownBy(() -> repository.delete(MeetingRoomId.create(999L), 1L))
                .isInstanceOf(MeetingRoomNotFoundException.class)
                .hasMessage("지정한 회의실 ID에 대한 예약을 찾지 못했습니다.");
    }

    @Test
    void 존재하지_않는_예약_ID로는_예약을_삭제할_수_없다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = reservationFactory.create(meetingRoom, organizer, timeSlot, 5);
        repository.save(reservation);

        // when & then
        assertThatThrownBy(() -> repository.delete(MeetingRoomId.create(1L), -999L))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessage("지정한 ID에 대한 예약을 찾지 못했습니다.");
    }

    @Test
    void 예약이_없는_회의실의_모든_예약을_조회하면_빈_목록을_반환한다() {
        // given & when
        Reservations actual = repository.findAll(MeetingRoomId.create(1L));

        // then
        assertThat(actual.getReservations()).isEmpty();
    }

    @Test
    void 회의실의_모든_예약을_조회한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot1 = createTimeSlot();
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusHours(1L)
        );
        Reservation reservation1 = reservationFactory.create(meetingRoom, organizer, timeSlot1, 5);
        Reservation reservation2 = reservationFactory.create(meetingRoom, organizer, timeSlot2, 5);
        repository.save(reservation1);
        repository.save(reservation2);

        // when
        Reservations actual = repository.findAll(MeetingRoomId.create(1L));

        // then
        assertThat(actual.getReservations()).hasSize(2);
    }

    @Test
    void 회의실별로_독립적인_예약_목록을_관리한다() {
        // given
        MeetingRoom meetingRoom1 = createMeetingRoom(1L);
        MeetingRoom meetingRoom2 = createMeetingRoom(2L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot1 = createTimeSlot();
        TimeSlot timeSlot2 = TimeSlot.create(
                LocalDateTime.now().plusDays(1L),
                LocalDateTime.now().plusDays(1L).plusHours(1L)
        );
        TimeSlot timeSlot3 = TimeSlot.create(
                LocalDateTime.now().plusDays(2L),
                LocalDateTime.now().plusDays(2L).plusHours(1L)
        );
        Reservation reservation1 = reservationFactory.create(meetingRoom1, organizer, timeSlot1, 5);
        Reservation reservation2 = reservationFactory.create(meetingRoom1, organizer, timeSlot2, 5);
        Reservation reservation3 = reservationFactory.create(meetingRoom2, organizer, timeSlot3, 5);

        // when
        repository.save(reservation1);
        repository.save(reservation2);
        repository.save(reservation3);

        Reservations room1Reservations = repository.findAll(MeetingRoomId.create(1L));
        Reservations room2Reservations = repository.findAll(MeetingRoomId.create(2L));

        // then
        assertAll(
                () -> assertThat(room1Reservations.getReservations()).hasSize(2),
                () -> assertThat(room2Reservations.getReservations()).hasSize(1)
        );
    }

    @Test
    void 예약을_조회한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = reservationFactory.create(meetingRoom, organizer, timeSlot, 5);
        Reservation saved = repository.save(reservation);

        // when
        Reservation actual = repository.find(MeetingRoomId.create(1L), saved.getId().getValue());

        // then
        assertThat(actual.getId().getValue()).isEqualTo(saved.getId().getValue());
    }

    @Test
    void 존재하지_않는_회의실의_예약은_조회할_수_없다() {
        // given & when & then
        assertThatThrownBy(() -> repository.find(MeetingRoomId.create(999L), 1L))
                .isInstanceOf(MeetingRoomNotFoundException.class)
                .hasMessage("지정한 회의실 ID에 대한 예약을 찾지 못했습니다.");
    }

    @Test
    void 존재하지_않는_예약_ID로는_예약을_조회할_수_없다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();
        Reservation reservation = reservationFactory.create(meetingRoom, organizer, timeSlot, 5);
        repository.save(reservation);

        // when & then
        assertThatThrownBy(() -> repository.find(MeetingRoomId.create(1L), -999L))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessage("지정한 ID에 대한 예약을 찾지 못했습니다.");
    }

    // 헬퍼 메서드
    private MeetingRoom createMeetingRoom(Long meetingRoomId) {
        return MeetingRoom.create(
                                  "회의실 A",
                                  10,
                                  MeetingRoomLocation.create(3, 1)
                          )
                          .withAssignedId(meetingRoomId);
    }

    private Organizer createOrganizer() {
        return Organizer.create("예약자", "010-1234-5678", "1234");
    }

    private TimeSlot createTimeSlot() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);
        return TimeSlot.create(startTime, endTime);
    }
}
