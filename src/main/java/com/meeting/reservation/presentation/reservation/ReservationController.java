package com.meeting.reservation.presentation.reservation;

import com.meeting.reservation.application.ReservationService;
import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import com.meeting.reservation.domain.reservation.vo.TimeSlot;
import com.meeting.reservation.presentation.reservation.dto.request.CancelReservationRequest;
import com.meeting.reservation.presentation.reservation.dto.request.RepeatReserveRequest;
import com.meeting.reservation.presentation.reservation.dto.request.ReserveRequest;
import com.meeting.reservation.presentation.reservation.dto.response.ReservationCollectionResponse;
import com.meeting.reservation.presentation.reservation.dto.response.ReservationResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms/{meetingRoomId}/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ReservationCollectionResponse> findAll(@PathVariable Long meetingRoomId) {
        List<Reservation> reservations = reservationService.findReservations(meetingRoomId);

        return ResponseEntity.ok()
                             .body(ReservationCollectionResponse.from(reservations));
    }

    @PostMapping
    public ResponseEntity<Void> reserve(@PathVariable Long meetingRoomId, @Valid @RequestBody ReserveRequest request) {
        Organizer organizer = Organizer.create(
                request.organizer().name(),
                request.organizer().phoneNumber(),
                request.organizer().password()
        );
        TimeSlot timeSlot = TimeSlot.create(request.startTime(), request.endTime());
        ReservationId reservationId = reservationService.reserve(
                meetingRoomId,
                organizer,
                timeSlot,
                request.attendeeCount()
        );
        URI location = URI.create("/rooms/" + meetingRoomId + "/reservations" + reservationId.getValue());

        return ResponseEntity.created(location)
                             .build();
    }

    @PostMapping("/repeat")
    public ResponseEntity<Void> repeatReserve(
            @PathVariable Long meetingRoomId,
            @Valid @RequestBody RepeatReserveRequest request
    ) {
        Organizer organizer = Organizer.create(
                request.organizer().name(),
                request.organizer().phoneNumber(),
                request.organizer().password()
        );
        TimeSlot timeSlot = TimeSlot.create(request.startTime(), request.endTime());

        reservationService.repeatReserve(
                meetingRoomId,
                organizer,
                timeSlot,
                request.attendeeCount(),
                request.frequency(),
                request.repeatCount()
        );

        URI location = URI.create("/rooms/" + meetingRoomId + "/reservations");

        return ResponseEntity.created(location)
                             .build();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> find(
            @PathVariable Long meetingRoomId,
            @PathVariable Long reservationId
    ) {
        Reservation reservation = reservationService.findReservation(meetingRoomId, reservationId);

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable Long meetingRoomId,
            @PathVariable Long reservationId,
            @Valid @RequestBody CancelReservationRequest request
    ) {
        reservationService.cancelReservation(meetingRoomId, reservationId, request.password());

        return ResponseEntity.noContent()
                             .build();
    }
}
