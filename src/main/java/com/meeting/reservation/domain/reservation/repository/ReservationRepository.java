package com.meeting.reservation.domain.reservation.repository;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.Reservations;

public interface ReservationRepository {

    Reservation save(Long meetingRoomId, Reservation reservation);

    void delete(Long meetingRoomId, Long id);

    Reservations findAll(Long meetingRoomId);
}
