package com.jiwoo.MeetingRoom.application;

import com.jiwoo.MeetingRoom.domain.MeetingRoom;
import com.jiwoo.MeetingRoom.infrastructure.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MeetingRoomService {

    MeetingRoomRepository meetingRoomRepository;

    @Autowired
    public MeetingRoomService(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    public ArrayList<MeetingRoom> findAll() {
        return meetingRoomRepository.findAll();
    }
}
