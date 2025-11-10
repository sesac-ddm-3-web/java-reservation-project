package com.meeting.reservation.domain.reservation.repository;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.Reservations;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void delete(MeetingRoomId meetingRoomId, Long id);

    Reservations findAll(MeetingRoomId meetingRoomId);
}
