package com.meeting.reservation.domain.reservation.repository;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.Reservations;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation target);

    List<Reservation> saveAll(List<Reservation> target);

    void delete(MeetingRoomId meetingRoomId, Long id);

    Reservation find(MeetingRoomId meetingRoomId, Long id);

    Reservations findAll(MeetingRoomId meetingRoomId);
}
