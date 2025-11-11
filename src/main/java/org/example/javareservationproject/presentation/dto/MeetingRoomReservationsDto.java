package org.example.javareservationproject.presentation.dto;

import java.util.List;

public record MeetingRoomReservationsDto(
    MeetingRoomDto meetingRoom,
    List<ReservationDto> reservations
) {
}
