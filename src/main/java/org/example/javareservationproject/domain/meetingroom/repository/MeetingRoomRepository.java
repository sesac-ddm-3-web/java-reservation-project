package org.example.javareservationproject.domain.meetingroom.repository;

import java.util.List;
import java.util.Optional;

import org.example.javareservationproject.domain.meetingroom.MeetingRoom;

public interface MeetingRoomRepository {

    List<MeetingRoom> findAll();

    Optional<MeetingRoom> findById(Long id);

    List<MeetingRoom> findByCapacity(Integer capacity);
}
