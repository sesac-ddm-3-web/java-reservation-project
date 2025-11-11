package com.sesac.reservation.management.presentation;

import com.sesac.reservation.management.application.SimpleRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {
    SimpleRoomService simpleRoomService;

    @Autowired
    public ReservationController(SimpleRoomService simpleRoomService) {
        this.simpleRoomService = simpleRoomService;
    }

    // [API] 전체 회의실 목록을 조회하는 API 를 구현해야 합니다.
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public List<RoomDto> showAllRooms() {
        return simpleRoomService.showAllRooms();
    }
}
