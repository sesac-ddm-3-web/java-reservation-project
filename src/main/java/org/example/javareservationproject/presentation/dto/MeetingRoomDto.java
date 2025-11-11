package org.example.javareservationproject.presentation.dto;

import org.example.javareservationproject.domain.meetingroom.MeetingRoom;

public record MeetingRoomDto(
    long id,
    String name,
    int capacity
) {

    public static MeetingRoomDto toDto(MeetingRoom room) {
        return new MeetingRoomDto(room.getId(), room.getName(), room.getCapacity());
    }
}
