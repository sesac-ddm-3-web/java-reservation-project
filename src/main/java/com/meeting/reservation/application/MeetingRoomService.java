package com.meeting.reservation.application;

import com.meeting.reservation.domain.room.MeetingRoom;
import com.meeting.reservation.domain.room.repository.MeetingRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    public List<MeetingRoom> findAll() {
        return meetingRoomRepository.findAll()
                                    .getMeetingRooms();
    }

    public List<MeetingRoom> findAllAccommodating(int attendeeCount) {
        return meetingRoomRepository.findAll()
                                    .findAccommodating(attendeeCount);
    }
}
