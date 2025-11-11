package com.meeting.reservation.application;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.Reservations;
import com.meeting.reservation.domain.reservation.repository.ReservationRepository;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import com.meeting.reservation.domain.room.MeetingRoom;
import com.meeting.reservation.domain.room.MeetingRooms;
import com.meeting.reservation.domain.room.repository.MeetingRoomRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final Clock clock;
    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservationRepository reservationRepository;

    public ReservationId reserve(
            Long meetingRoomId,
            Organizer organizer,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int attendeeCount
    ) {
        MeetingRooms meetingRooms = meetingRoomRepository.findAll();
        MeetingRoom meetingRoom = meetingRooms.findMeetingRoom(meetingRoomId);

        meetingRoom.validateAttendeeCount(attendeeCount);

        Reservations reservations = reservationRepository.findAll(meetingRoom.getId());
        Reservation reservation = Reservation.create(meetingRoom.getId(), organizer, startTime, endTime, attendeeCount);

        reservations.validateReserve(reservation);

        return reservationRepository.save(reservation)
                                    .getId();
    }

    public List<Reservation> findReservations(Long meetingRoomId) {
        MeetingRooms meetingRooms = meetingRoomRepository.findAll();
        MeetingRoom meetingRoom = meetingRooms.findMeetingRoom(meetingRoomId);

        return reservationRepository.findAll(meetingRoom.getId())
                                    .getReservations();
    }

    public Reservation findReservation(Long meetingRoomId, Long reservationId) {
        MeetingRooms meetingRooms = meetingRoomRepository.findAll();
        MeetingRoom meetingRoom = meetingRooms.findMeetingRoom(meetingRoomId);

        return reservationRepository.find(meetingRoom.getId(), reservationId);
    }

    public void cancelReservation(Long meetingRoomId, Long reservationId, String password) {
        MeetingRooms meetingRooms = meetingRoomRepository.findAll();
        MeetingRoom meetingRoom = meetingRooms.findMeetingRoom(meetingRoomId);
        Reservations reservations = reservationRepository.findAll(meetingRoom.getId());

        reservations.validateCancel(reservationId, password, LocalDateTime.now(clock));

        reservationRepository.delete(meetingRoom.getId(), reservationId);
    }
}
