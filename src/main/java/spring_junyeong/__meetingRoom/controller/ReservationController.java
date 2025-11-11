package spring_junyeong.__meetingRoom.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring_junyeong.__meetingRoom.domain.dto.ReservationDto;
import spring_junyeong.__meetingRoom.domain.dto.ReservationResponseDto;
import spring_junyeong.__meetingRoom.service.ReservationService;

import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    // 예약 - 새 예약 생성
    @RequestMapping(value = "/rooms/{roomId}/reservations", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDto createReservation(@Valid @RequestBody ReservationDto reservationDto, @PathVariable Long roomId) {
        // reservation을 어디에 만들지 -> roomId -> URI(path variable)
        // reservation이 어떤 내용인지 -> 생성할 리소스의 속성 = Dto -> 예약할 방과 예약을 구분함으로써, DTO는 예약 자체인 순수한 객체로 남게됨.
        return reservationService.createReservation(reservationDto, roomId);
    }

    // 예약 - 특정 룸에 대한 전체 예약 정보 조회
    @RequestMapping(value = "/rooms/{roomId}/reservations", method = RequestMethod.GET)
    public List<ReservationResponseDto> getReservationsByRoomId(@PathVariable Long roomId){
        return reservationService.getReservationsByRoomId(roomId);
    }

    // 예약 - 예약 취소
    @RequestMapping(value = "/reservations/{reservationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReservation(@PathVariable Long reservationId, @RequestBody String password){
        reservationService.removeReservation(reservationId, password);
    }
}

