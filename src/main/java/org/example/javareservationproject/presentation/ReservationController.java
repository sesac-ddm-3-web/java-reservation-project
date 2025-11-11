package org.example.javareservationproject.presentation;

import org.example.javareservationproject.presentation.dto.DeleteReservationReqDto;
import org.example.javareservationproject.presentation.dto.ReservationReqDto;
import org.example.javareservationproject.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/meeting-rooms/{room_id}/reservations")
    public ResponseEntity<String> makeReservation(
        @PathVariable("room_id") long roomId,
        @Valid @RequestBody ReservationReqDto request
    ) {
        reservationService.makeReservation(roomId, request);
        return new ResponseEntity<>("예약에 성공했습니다.", HttpStatus.CREATED);
    }

    @DeleteMapping("/meeting-rooms/{room_id}/reservations/{reservation_id}")
    public ResponseEntity<String> deleteReservation(
        @PathVariable("room_id") long roomId,
        @PathVariable("reservation_id") long reservationId,
        @Valid @RequestBody DeleteReservationReqDto request
    ) {
        reservationService.delete(roomId, reservationId, request.password());
        return new ResponseEntity<>("예약 삭제에 성공했습니다.", HttpStatus.OK);
    }
}
