package com.example.sesac.MeetingRoomProject.presentation;

import com.example.sesac.MeetingRoomProject.application.MeetingRoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @RequestMapping(path = "/meetingRooms", method = RequestMethod.GET)
    public List<MeetingRoomDto> getMeetingRooms() {
        return meetingRoomService.getMeetingRooms();
    }
}
