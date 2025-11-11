package com.example.assignmant.controller;


import com.example.assignmant.dto.ReservationDeleteDto;
import com.example.assignmant.dto.ReservationDto;
import com.example.assignmant.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/rooms/{roomId}/reservations")
    public ResponseEntity<ReservationDto> createReservation(
            @PathVariable Long roomId,
            @Valid @RequestBody ReservationDto reservationDto
    ) {
        ReservationDto createdReservation = reservationService.createReservation(roomId, reservationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @GetMapping("/rooms/{roomId}/reservations")
    public ResponseEntity<List<ReservationDto>> findAllReservations(@PathVariable Long roomId) {
        List<ReservationDto> reservations = reservationService.findAllReservations(roomId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/rooms/{roomId}/reservations/{reservationId}")
    public ResponseEntity<ReservationDto> findByReservationIdInRoom(
            @PathVariable Long roomId,
            @PathVariable Long reservationId
    ) {
        ReservationDto reservation = reservationService.findByReservationIdInRoom(roomId, reservationId);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/rooms/{roomId}/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long roomId,
            @PathVariable Long reservationId,
            @Valid @RequestBody ReservationDeleteDto deleteDto
    ) {
        reservationService.deleteReservation(roomId, reservationId, deleteDto);
        return ResponseEntity.noContent().build();
    }

}
