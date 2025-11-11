package org.example.javareservationproject.presentation;

import org.example.javareservationproject.presentation.dto.MeetingRoomReservationsDto;
import org.example.javareservationproject.presentation.dto.MeetingRoomsDto;
import org.example.javareservationproject.service.MeetingRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @GetMapping("/meeting-rooms")
    public ResponseEntity<MeetingRoomsDto> getAllMeetingRooms() {
        return ResponseEntity.ok(meetingRoomService.getAllMeetingRoomInfo());
    }

    @GetMapping("/meeting-rooms/{room_id}/reservations")
    public ResponseEntity<MeetingRoomReservationsDto> getAllRoomReservations(
        @PathVariable("room_id") long roomId
    ) {
        MeetingRoomReservationsDto response = meetingRoomService.getMeetingRoomReservations(roomId);
        return ResponseEntity.ok(response);
    }
}
