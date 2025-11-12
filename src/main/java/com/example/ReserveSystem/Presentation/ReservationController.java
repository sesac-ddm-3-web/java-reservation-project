package com.example.ReserveSystem.Presentation;


import com.example.ReserveSystem.Application.SimpleReserveService;
import com.example.ReserveSystem.Domain.MeetingRoom;
import com.example.ReserveSystem.Domain.Reservation;
import jakarta.validation.Valid;
import org.springframework.beans.Mergeable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    // 의존성 주입
    private SimpleReserveService simpleReserveService;

    @Autowired
    ReservationController(SimpleReserveService simpleReserveService){
        this.simpleReserveService = simpleReserveService;
    }


    @RequestMapping(path = "/meetingrooms", method = RequestMethod.GET)
    public List<MeetingRoom> findMeetingRoomByAll(){
        return simpleReserveService.findMeetingRoomByAll();
    }

    @RequestMapping(path = "/reservation", method = RequestMethod.POST)
    public ReservationDto createReservation(@Valid @RequestBody ReservationDto reservationDto){
        return simpleReserveService.createReservation(reservationDto);
    }

    @RequestMapping(path="/reservation/{roomId}", method = RequestMethod.GET)
    public List<ReservationDto> findReservationsByRoomId(@PathVariable Integer roomId){
        return simpleReserveService.findReservationsByRoomId(roomId);
    }

    @RequestMapping(path="/reservation/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id, @Valid @RequestBody PasswordDto passwordDto){
        simpleReserveService.delete(id, passwordDto);
    }

}
