package com.meeting.reservation.presentation.reservation;

import com.meeting.reservation.application.ReservationService;
import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import com.meeting.reservation.presentation.reservation.dto.request.CancelReservationRequest;
import com.meeting.reservation.presentation.reservation.dto.request.ReserveRequest;
import com.meeting.reservation.presentation.reservation.dto.response.ReservationCollectionResponse;
import com.meeting.reservation.presentation.reservation.dto.response.ReservationCollectionResponse.OrganizerResponse;
import com.meeting.reservation.presentation.reservation.dto.response.ReservationCollectionResponse.ReservationResponse;
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
        List<ReservationResponse> responses = reservationService.findReservations(meetingRoomId)
                                                                          .stream()
                                                                          .map(this::mapToReservationResponse)
                                                                          .toList();

        return ResponseEntity.ok()
                .body(new ReservationCollectionResponse(responses));
    }

    private ReservationResponse mapToReservationResponse(Reservation reservation) {
        OrganizerResponse organizerResponse = mapToOrganizerResponse(reservation.getOrganizer());

        return new ReservationResponse(
                reservation.getId().getValue(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                organizerResponse
        );
    }

    private OrganizerResponse mapToOrganizerResponse(Organizer organizer) {
        return new OrganizerResponse(organizer.getName(), organizer.getPhoneNumber(), organizer.getPassword());
    }

    @PostMapping
    public ResponseEntity<Void> reserve(@PathVariable Long meetingRoomId, @Valid @RequestBody ReserveRequest request) {
        Organizer organizer = Organizer.create(
                request.organizer().name(),
                request.organizer().phoneNumber(),
                request.organizer().password()
        );

        ReservationId reservationId = reservationService.reserve(
                meetingRoomId,
                organizer,
                request.startTime(),
                request.endTime()
        );

        return ResponseEntity.created(
                                     URI.create("/rooms/" + meetingRoomId + "/reservations" + reservationId.getValue())
                             )
                             .build();
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
