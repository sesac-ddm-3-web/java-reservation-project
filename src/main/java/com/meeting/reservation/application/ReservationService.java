package com.meeting.reservation.application;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.Reservations;
import com.meeting.reservation.domain.reservation.repository.ReservationRepository;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final Clock clock;
    private final ReservationRepository reservationRepository;

    public ReservationId reserve(
            Long meetingRoomId,
            Organizer organizer,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        Reservation reservation = Reservation.create(organizer, startTime, endTime);
        Reservations reservations = reservationRepository.findAll(meetingRoomId);

        reservations.validateReserve(reservation);

        return reservationRepository.save(meetingRoomId, reservation)
                                    .getId();
    }

    public List<Reservation> findReservations(Long meetingRoomId) {
        return reservationRepository.findAll(meetingRoomId)
                                    .getReservations();
    }

    public void cancelReservation(Long meetingRoomId, Long reservationId, String password) {
        Reservations reservations = reservationRepository.findAll(meetingRoomId);

        reservations.validateCancel(reservationId, password, LocalDateTime.now(clock));

        reservationRepository.delete(meetingRoomId, reservationId);
    }
}
