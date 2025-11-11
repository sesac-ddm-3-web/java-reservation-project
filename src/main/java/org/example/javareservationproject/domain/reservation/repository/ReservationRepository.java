package org.example.javareservationproject.domain.reservation.repository;

import java.util.Optional;

import org.example.javareservationproject.domain.reservation.Reservation;
import org.example.javareservationproject.domain.reservation.RoomReservations;

public interface ReservationRepository {

    RoomReservations findByMeetingRoomId(Long meetingRoomId);

    Optional<Reservation> findByIdAndMeetingRoomId(Long meetingRoomId, Long id);

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);
}
