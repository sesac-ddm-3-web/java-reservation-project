package com.jiwoo.MeetingRoom.presentation;

import com.jiwoo.MeetingRoom.application.MeetingRoomService;
import com.jiwoo.MeetingRoom.domain.MeetingRoom;
import com.jiwoo.MeetingRoom.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MeetingRoomController {

    MeetingRoomService meetingRoomService;

    @Autowired
    public void setMeetingRoomService(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public ArrayList<MeetingRoom> findAllRooms() {
        return meetingRoomService.findAll();
    }

}
