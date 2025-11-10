package spring_junyeong.__meetingRoom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_junyeong.__meetingRoom.repository.MeetingRoomRepository;

@Service
public class MeetingRoomService {
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    MeetingRoomService(MeetingRoomRepository meetingRoomRepository){
        this.meetingRoomRepository = meetingRoomRepository;
    }
}
