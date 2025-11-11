package com.sesac.reservation.management.presentation;

import com.sesac.reservation.management.application.SimpleReservationService;
import com.sesac.reservation.management.application.SimpleRoomService;
import com.sesac.reservation.management.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    SimpleRoomService simpleRoomService;
    SimpleReservationService simpleReservationService;

    @Autowired
    public ReservationController(SimpleRoomService simpleRoomService, SimpleReservationService simpleReservationService) {
        this.simpleRoomService = simpleRoomService;
        this.simpleReservationService = simpleReservationService;
    }

    // [API] 전체 회의실 목록을 조회하는 API를 구현해야 합니다.
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public List<RoomDto> showAllRooms() {
        return simpleRoomService.showAllRooms();
    }

    // [API] 새 예약을 생성하는 API를 구현해야 합니다.
    @RequestMapping(value = "/reservations", method = RequestMethod.POST)
    public ReservationDto createReservations(@RequestBody ReservationDto reservationDto) {
        return simpleReservationService.add(reservationDto);
    }

    // [API] 특정 회의실의 모든 예약 현황을 조회하는 API를 구현해야 합니다.
    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public List<ReservationDto> findReservationsByRoomId(
            @RequestParam(required = false) Integer roomId
    ) {
        if(roomId == null) {
            return simpleReservationService.findAll();
        }

        return simpleReservationService.findByRoomId(roomId);
    }
}
