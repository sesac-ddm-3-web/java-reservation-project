package org.example.javareservationproject.presentation.dto;

import java.util.List;

public record MeetingRoomsDto(
    List<MeetingRoomDto> meetingRooms
) {
}
