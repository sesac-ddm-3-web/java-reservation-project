package com.example.reservation.presentation;

import com.example.reservation.application.ReservationService;
import com.example.reservation.domain.Room;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 1. 새 예약
    @RequestMapping(value = "/booking", method = RequestMethod.POST)
    public ReservationDto createReservation(@Valid @RequestBody ReservationDto reservationDto) {
        return reservationService.add(reservationDto);
    }

    // 2. 특정 방 예약 조회
    @RequestMapping(value = "/booking/{roomId}", method = RequestMethod.GET)
    public List<ReservationDto> findReservationByRoom(@PathVariable Long roomId) {
        return reservationService.findReservationByRoom(roomId);
    }

    // 3. 전체 회의실 목록 조회
    @RequestMapping(value = "/booking/room", method = RequestMethod.GET)
    public List<Room> findAllRoom() {
        return reservationService.findAllRoom();
    }

    // 4. 예약 삭제
    @RequestMapping(value = "/booking/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id, @RequestParam String password) {
        reservationService.delete(id, password);
    }

}
