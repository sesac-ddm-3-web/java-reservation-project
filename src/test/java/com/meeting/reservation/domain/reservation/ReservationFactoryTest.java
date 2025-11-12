package com.meeting.reservation.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.meeting.reservation.domain.reservation.vo.EquipmentUsages;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import com.meeting.reservation.domain.reservation.vo.TimeSlot;
import com.meeting.reservation.domain.room.MeetingRoom;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import com.meeting.reservation.domain.room.vo.MeetingRoomLocation;
import com.meeting.reservation.persistence.InMemoryEquipmentRepository;
import com.meeting.reservation.persistence.InMemoryReservationRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationFactoryTest {

    private final InMemoryReservationRepository reservationRepository = new InMemoryReservationRepository();
    private final InMemoryEquipmentRepository equipmentRepository = new InMemoryEquipmentRepository();
    private final ReservationFactory reservationFactory = new ReservationFactory(equipmentRepository, reservationRepository);

    @Test
    void 예약을_생성한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();

        // when
        Reservation actual = reservationFactory.create(
                meetingRoom,
                organizer,
                timeSlot,
                5,
                List.of()
        );

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getMeetingRoomId().getValue()).isEqualTo(1L),
                () -> assertThat(actual.getAttendeeCount()).isEqualTo(5),
                () -> assertThat(actual.getOrganizer()).isEqualTo(organizer)
        );
    }

    @Test
    void 예약자_정보가_없으면_예외가_발생한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        TimeSlot timeSlot = createTimeSlot();

        // when & then
        assertThatThrownBy(() -> reservationFactory.create(
                meetingRoom,
                null,
                timeSlot,
                5,
                List.of()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 정보는 비어 있을 수 없습니다.");
    }

    @Test
    void 예약_시간이_없으면_예외가_발생한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();

        // when & then
        assertThatThrownBy(() -> reservationFactory.create(
                meetingRoom,
                organizer,
                null,
                5,
                List.of()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 비어 있을 수 없습니다.");
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @ValueSource(ints = {0, -1})
    void 참가_인원이_양수가_아니면_예외가_발생한다(int attendeeCount) {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();

        // when & then
        assertThatThrownBy(() -> reservationFactory.create(
                meetingRoom,
                organizer,
                timeSlot,
                attendeeCount,
                List.of()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("참가 인원은 양수여야 합니다.");
    }

    @Test
    void 회의실_수용_인원을_초과하면_예외가_발생한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();

        // when & then
        assertThatThrownBy(() -> reservationFactory.create(
                meetingRoom,
                organizer,
                timeSlot,
                100,
                List.of()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 회의실은 참가 인원을 전부 수용할 수 없습니다.");
    }

    @Test
    void 예약_시간이_중복되면_예외가_발생한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        TimeSlot timeSlot = createTimeSlot();
        Organizer organizer = createOrganizer();

        Reservation existingReservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                MeetingRoomId.create(1L),
                timeSlot,
                5,
                organizer,
                EquipmentUsages.create(Collections.emptyMap())
        );
        reservationRepository.save(existingReservation);

        // when & then
        assertThatThrownBy(() -> reservationFactory.create(
                meetingRoom,
                organizer,
                timeSlot,
                5,
                List.of()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 시간이 겹치는 예약이 존재합니다.");
    }

    @Test
    void 반복_예약을_생성한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();

        // when
        List<Reservation> actual = reservationFactory.create(
                meetingRoom,
                organizer,
                timeSlot,
                5,
                ReservationFrequency.DAILY,
                2,
                List.of()
        );

        // then
        assertThat(actual).hasSize(3);
    }

    @Test
    void 반복_예약이_올바른_간격으로_생성된다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = TimeSlot.create(
                LocalDateTime.of(2025, 11, 11, 10, 0),
                LocalDateTime.of(2025, 11, 11, 12, 0)
        );

        // when
        List<Reservation> actual = reservationFactory.create(
                meetingRoom,
                organizer,
                timeSlot,
                5,
                ReservationFrequency.WEEKLY,
                2,
                List.of()
        );

        // then
        assertAll(
                () -> assertThat(actual.get(0).getTimeSlot().getStartTime()).isEqualTo(LocalDateTime.of(2025, 11, 11, 10, 0)),
                () -> assertThat(actual.get(1).getTimeSlot().getStartTime()).isEqualTo(LocalDateTime.of(2025, 11, 18, 10, 0)),
                () -> assertThat(actual.get(2).getTimeSlot().getStartTime()).isEqualTo(LocalDateTime.of(2025, 11, 25, 10, 0))
        );
    }

    @Test
    void 반복_예약의_모든_예약이_같은_정보를_공유한다() {
        // given
        MeetingRoom meetingRoom = createMeetingRoom(1L);
        Organizer organizer = createOrganizer();
        TimeSlot timeSlot = createTimeSlot();

        // when
        List<Reservation> actual = reservationFactory.create(
                meetingRoom,
                organizer,
                timeSlot,
                5,
                ReservationFrequency.DAILY,
                1,
                List.of()
        );

        // then
        assertAll(
                () -> assertThat(actual.get(0).getMeetingRoomId()).isEqualTo(actual.get(1).getMeetingRoomId()),
                () -> assertThat(actual.get(0).getOrganizer()).isEqualTo(actual.get(1).getOrganizer()),
                () -> assertThat(actual.get(0).getAttendeeCount()).isEqualTo(actual.get(1).getAttendeeCount())
        );
    }

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
