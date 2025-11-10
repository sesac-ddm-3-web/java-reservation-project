package com.meeting.reservation.presentation.room;

import com.meeting.reservation.application.MeetingRoomService;
import com.meeting.reservation.domain.room.MeetingRoom;
import com.meeting.reservation.domain.room.vo.MeetingRoomLocation;
import com.meeting.reservation.presentation.room.dto.response.MeetingRoomCollectionResponse;
import com.meeting.reservation.presentation.room.dto.response.MeetingRoomCollectionResponse.MeetingRoomResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @GetMapping
    public ResponseEntity<MeetingRoomCollectionResponse> findAll() {
        List<MeetingRoomResponse> responses = meetingRoomService.findAll()
                                                                .stream()
                                                                .map(this::mapToMeetingRoomResponse)
                                                                .toList();

        return ResponseEntity.ok(new MeetingRoomCollectionResponse(responses));
    }

    private MeetingRoomResponse mapToMeetingRoomResponse(MeetingRoom meetingRoom) {
        MeetingRoomLocation location = meetingRoom.getLocation();

        return new MeetingRoomResponse(
                meetingRoom.getId().getValue(),
                meetingRoom.getName(),
                location.getFloor(),
                location.getRoomNumber()
        );
    }
}
