package spring_junyeong.__meetingRoom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring_junyeong.__meetingRoom.domain.MeetingRoom;
import spring_junyeong.__meetingRoom.domain.Reservation;
import spring_junyeong.__meetingRoom.service.MeetingRoomService;
import spring_junyeong.__meetingRoom.service.ReservationService;

import java.util.List;

@RestController
public class MeetingRoomController {

    private MeetingRoomService meetingRoomService;
    private ReservationService reservationService;

    @Autowired
    MeetingRoomController(MeetingRoomService meetingRoomService, ReservationService reservationService) {
        this.meetingRoomService = meetingRoomService;
        this.reservationService = reservationService;
    }

    @RequestMapping(value = "/meetingRoom", method = RequestMethod.GET)
    public List<MeetingRoom> getMeetingRoomList(){
        return meetingRoomService.getMeetingRoomList();
    }

    @RequestMapping(value = "/meetingRoom/{id}", method = RequestMethod.GET)
    public List<MeetingRoom> getMeetingRoomById(@PathVariable String id){
        return meetingRoomService.getMeetingRoomById(id);
    }


}
