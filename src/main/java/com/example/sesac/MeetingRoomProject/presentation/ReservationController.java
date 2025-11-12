package com.example.sesac.MeetingRoomProject.presentation;

import com.example.sesac.MeetingRoomProject.application.ReservationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(path = "/meetingRooms/{roomId}/reservations", method = RequestMethod.POST)
    public ReservationDto createReservation(@PathVariable Long roomId,
                                            @RequestBody @Valid ReservationDto reservationDto) {
        return reservationService.createReservation(roomId, reservationDto);
    }

    @RequestMapping(path = "/meetingRooms/{roomId}/reservations", method = RequestMethod.GET)
    public List<ReservationDto> getReservationsByRoomId(@PathVariable Long roomId){
        return reservationService.getReservationsByRoomId(roomId);
    }

    @RequestMapping(path = "/meetingRooms/{roomId}/reservations/{reservationId}", method = RequestMethod.DELETE)
    public void deleteReservation(@PathVariable Long roomId,
                                  @PathVariable Long reservationId,
                                  @RequestBody PasswordDto passwordDto) {
        reservationService.deleteReservation(roomId, reservationId, passwordDto.getPassword());
    }
}
