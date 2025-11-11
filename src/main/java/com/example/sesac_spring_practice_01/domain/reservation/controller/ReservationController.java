package com.example.sesac_spring_practice_01.domain.reservation.controller;


import com.example.sesac_spring_practice_01.domain.reservation.dto.request.ReservationCancelReqDto;
import com.example.sesac_spring_practice_01.domain.reservation.dto.request.ReservationCreateReqDto;
import com.example.sesac_spring_practice_01.domain.reservation.dto.response.ReservationCompleteResDto;
import com.example.sesac_spring_practice_01.domain.reservation.dto.response.ReservationStatusResDto;
import com.example.sesac_spring_practice_01.domain.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{roomId}/reservations")
    public List<ReservationStatusResDto> getAllReservations(@PathVariable Long roomId) {
        return reservationService.getAllReservations(roomId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{roomId}/reservations")
    public ReservationCompleteResDto createReservation(@PathVariable Long roomId,
                                                       @Valid @RequestBody ReservationCreateReqDto request){
        return reservationService.createReservation(roomId, request);
    }

    @DeleteMapping("/{roomId}/reservations/{reservationId}")
    public void deleteReservation(@PathVariable Long roomId,
                                  @PathVariable Long reservationId,
                                  @Valid @RequestBody ReservationCancelReqDto request){
        reservationService.deleteReservation(roomId, reservationId, request);
    }
}
