package com.meeting.reservation.presentation.room.dto.response;

import java.util.List;

public record MeetingRoomCollectionResponse(List<MeetingRoomResponse> meetingRooms) {

    public record MeetingRoomResponse(Long id, String name, int floor, int roomNumber) {
    }
}
