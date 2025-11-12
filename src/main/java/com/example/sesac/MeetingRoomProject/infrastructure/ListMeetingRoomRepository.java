package com.example.sesac.MeetingRoomProject.infrastructure;

import com.example.sesac.MeetingRoomProject.domain.MeetingRoom;
import com.example.sesac.MeetingRoomProject.exceptions.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListMeetingRoomRepository {
    List<MeetingRoom> meetingRooms = new CopyOnWriteArrayList<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    @PostConstruct
    public void init(){
        meetingRooms.add(new MeetingRoom(sequence.getAndIncrement(),"회의실 1" ));
        meetingRooms.add(new MeetingRoom(sequence.getAndIncrement(),"회의실 2"));
        meetingRooms.add(new MeetingRoom(sequence.getAndIncrement(), "회의실 3"));
        meetingRooms.add(new MeetingRoom(sequence.getAndIncrement(), "회의실 4"));
    }

    public List<MeetingRoom> getMeetingRooms(){
        return meetingRooms;
    }

    public MeetingRoom getMeetingRoomById(Long id){
        return meetingRooms.stream()
                .filter(meetingRoom -> meetingRoom.sameId(id))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("MeetingRoom을 찾지 못했습니다."));
    }
}
