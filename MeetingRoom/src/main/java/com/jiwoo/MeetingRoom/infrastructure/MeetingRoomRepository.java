package com.jiwoo.MeetingRoom.infrastructure;

import com.jiwoo.MeetingRoom.domain.MeetingRoom;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MeetingRoomRepository {

    ArrayList<MeetingRoom> meetingRooms = new ArrayList<>();
    AtomicLong sequence = new AtomicLong(1L);

    public MeetingRoomRepository() {
        meetingRooms.add(new MeetingRoom(sequence.getAndIncrement(),"1번방"));
        meetingRooms.add(new MeetingRoom(sequence.getAndIncrement(),"2번방"));
        meetingRooms.add(new MeetingRoom(sequence.getAndIncrement(),"3번방"));
    }

    public ArrayList<MeetingRoom> findAll() {
        return meetingRooms;
    }

    public MeetingRoom findById(Long id) {
        return meetingRooms.stream().filter(room -> room.getId().equals(id)).findFirst().orElse(null);
    }

}
