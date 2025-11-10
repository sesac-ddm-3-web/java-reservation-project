package com.meeting.reservation.domain.room;

import java.util.Collections;
import java.util.List;

public class MeetingRooms {

    private final List<MeetingRoom> values;

    public static MeetingRooms create(List<MeetingRoom> values) {
        return new MeetingRooms(values);
    }

    private MeetingRooms(List<MeetingRoom> values) {
        this.values = values;
    }

    public MeetingRoom findMeetingRoom(Long id) {
        return values.stream()
                     .filter(meetingRoom -> meetingRoom.isEqualId(id))
                     .findAny()
                     .orElseThrow(() -> new MeetingRoomNotFoundException("지정한 ID에 해당하는 회의실을 찾을 수 없습니다."));
    }

    public List<MeetingRoom> getMeetingRooms() {
        return Collections.unmodifiableList(values);
    }

    public static class MeetingRoomNotFoundException extends IllegalArgumentException {

        MeetingRoomNotFoundException(String s) {
            super(s);
        }
    }
}
