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

    public void validateCapacity(Long id, int attendeeCount) {
        MeetingRoom meetingRoom = findMeetingRoom(id);

        if (!meetingRoom.canAccommodate(attendeeCount)) {
            throw new IllegalArgumentException("해당 회의실은 참가 인원을 전부 수용할 수 없습니다.");
        }
    }

    public MeetingRoom findMeetingRoom(Long id) {
        return values.stream()
                     .filter(meetingRoom -> meetingRoom.isEqualId(id))
                     .findAny()
                     .orElseThrow(() -> new MeetingRoomNotFoundException("지정한 ID에 해당하는 회의실을 찾을 수 없습니다."));
    }

    public List<MeetingRoom> findAccommodating(int attendeeCount) {
        return values.stream()
                     .filter(meetingRoom -> meetingRoom.canAccommodate(attendeeCount))
                     .toList();
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
