package com.example.sesac_spring_practice_01.domain.reservation.controller;


import com.example.sesac_spring_practice_01.domain.reservation.dto.request.ReservationCancelReqDto;
import com.example.sesac_spring_practice_01.domain.reservation.dto.request.ReservationCreateReqDto;
import com.example.sesac_spring_practice_01.domain.reservation.dto.response.ReservationCompleteResDto;
import com.example.sesac_spring_practice_01.domain.reservation.dto.response.ReservationStatusResDto;
import com.example.sesac_spring_practice_01.domain.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{roomId}/reservations")
    public ResponseEntity<List<ReservationStatusResDto>> getAllReservations(@PathVariable Long roomId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.getAllReservations(roomId));
    }

    @PostMapping("/{roomId}/reservations")
    public ResponseEntity<ReservationCompleteResDto> createReservation(@PathVariable Long roomId,
                                                       @Valid @RequestBody ReservationCreateReqDto request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.createReservation(roomId, request));
    }

    @DeleteMapping("/{roomId}/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long roomId,
                                  @PathVariable Long reservationId,
                                  @Valid @RequestBody ReservationCancelReqDto request){
        reservationService.deleteReservation(roomId, reservationId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
