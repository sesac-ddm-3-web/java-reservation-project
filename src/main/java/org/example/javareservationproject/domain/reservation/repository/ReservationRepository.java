package org.example.javareservationproject.domain.reservation.repository;

import java.util.Optional;

import org.example.javareservationproject.domain.reservation.Reservation;
import org.example.javareservationproject.domain.reservation.Reservations;

public interface ReservationRepository {

    Reservations findByMeetingRoomId(Long meetingRoomId);

    Optional<Reservation> findByIdAndMeetingRoomId(Long meetingRoomId, Long id);

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);
}
