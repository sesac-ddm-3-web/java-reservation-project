package com.meeting.reservation.persistence;

import com.meeting.reservation.domain.room.MeetingRoom;
import com.meeting.reservation.domain.room.MeetingRooms;
import com.meeting.reservation.domain.room.repository.MeetingRoomRepository;
import com.meeting.reservation.domain.room.vo.MeetingRoomLocation;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryMeetingRoomRepository implements MeetingRoomRepository {

    private final MeetingRooms meetingRooms;

    public InMemoryMeetingRoomRepository() {
        this.meetingRooms = MeetingRooms.create(initMeetingRooms());
    }

    private List<MeetingRoom> initMeetingRooms() {
        return List.of(
                MeetingRoom.create("회의실 101", 2, MeetingRoomLocation.create(1, 1)).withAssignedId(1L),
                MeetingRoom.create("회의실 102", 3, MeetingRoomLocation.create(1, 2)).withAssignedId(2L),
                MeetingRoom.create("회의실 103", 4, MeetingRoomLocation.create(1, 3)).withAssignedId(3L),
                MeetingRoom.create("회의실 201", 5, MeetingRoomLocation.create(2, 1)).withAssignedId(4L),
                MeetingRoom.create("회의실 202", 8, MeetingRoomLocation.create(2, 2)).withAssignedId(5L),
                MeetingRoom.create("회의실 301", 16, MeetingRoomLocation.create(3, 1)).withAssignedId(6L),
                MeetingRoom.create("지하 1층 1번 회의실", 32, MeetingRoomLocation.create(-1, 1)).withAssignedId(7L)
        );
    }

    @Override
    public MeetingRooms findAll() {
        return this.meetingRooms;
    }
}
