package com.meeting.reservation.presentation.reservation.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationCollectionResponse(List<ReservationResponse> reservations) {

    public record ReservationResponse(
            Long id,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int attendeeCount,
            OrganizerResponse organizer
    ) {
    }

    public record OrganizerResponse(String name, String phoneNumber, String password) {
    }
}
