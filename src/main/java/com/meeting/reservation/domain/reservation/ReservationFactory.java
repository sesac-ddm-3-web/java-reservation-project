package com.meeting.reservation.domain.reservation;

import com.meeting.reservation.domain.reservation.repository.ReservationRepository;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import com.meeting.reservation.domain.reservation.vo.TimeSlot;
import com.meeting.reservation.domain.room.MeetingRoom;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class ReservationFactory {

    private final ReservationRepository reservationRepository;

    public Reservation create(
            MeetingRoom meetingRoom,
            Organizer organizer,
            TimeSlot timeSlot,
            int attendeeCount
    ) {
        meetingRoom.validateAttendeeCount(attendeeCount);

        Reservations reservations = reservationRepository.findAll(meetingRoom.getId());

        validateOrganizer(organizer);
        validateTimeSlot(timeSlot);
        validateAttendeeCount(attendeeCount);

        Reservation reservation = new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoom.getId(),
                timeSlot,
                attendeeCount,
                organizer
        );

        reservations.validateReserve(reservation);

        return reservation;
    }

    public List<Reservation> create(
            MeetingRoom meetingRoom,
            Organizer organizer,
            TimeSlot timeSlot,
            int attendeeCount,
            ReservationFrequency frequency,
            int repeatCount
    ) {
        Reservation startReservation = this.create(
                meetingRoom,
                organizer,
                timeSlot,
                attendeeCount
        );
        List<Reservation> reservationList = new ArrayList<>();

        reservationList.add(startReservation);
        Reservation currentReservation = startReservation;

        for (int i = 0; i < repeatCount; i++) {
            Reservation shiftReservation = currentReservation.shift(frequency);
            reservationList.add(shiftReservation);
            currentReservation = shiftReservation;
        }

        return reservationList;
    }

    private void validateOrganizer(Organizer organizer) {
        if (organizer == null) {
            throw new IllegalArgumentException("예약자 정보는 비어 있을 수 없습니다.");
        }
    }

    private void validateAttendeeCount(int attendeeCount) {
        if (attendeeCount <= 0) {
            throw new IllegalArgumentException("참가 인원은 양수여야 합니다.");
        }
    }

    private void validateTimeSlot(TimeSlot timeSlot) {
        if (timeSlot == null) {
            throw new IllegalArgumentException("예약 시간은 비어 있을 수 없습니다.");
        }
    }
}
