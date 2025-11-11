package com.meeting.reservation.presentation.reservation.dto.response;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int attendeeCount,
        OrganizerResponse organizer
) {

    public static ReservationResponse from(Reservation reservation) {
        OrganizerResponse organizerResponse = OrganizerResponse.from(reservation.getOrganizer());

        return new ReservationResponse(
                reservation.getId().getValue(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getAttendeeCount(),
                organizerResponse
        );
    }

    public record OrganizerResponse(String name, String phoneNumber, String password) {

        private static OrganizerResponse from(Organizer organizer) {
            return new OrganizerResponse(organizer.getName(), organizer.getPhoneNumber(), organizer.getPassword());
        }
    }
}
