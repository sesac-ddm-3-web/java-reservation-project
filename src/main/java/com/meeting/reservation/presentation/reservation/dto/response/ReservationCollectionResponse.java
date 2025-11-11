package com.meeting.reservation.presentation.reservation.dto.response;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationCollectionResponse(List<ReservationResponse> reservations) {

    public static ReservationCollectionResponse from(List<Reservation> reservations) {
        List<ReservationResponse> responses = reservations.stream()
                                                     .map(ReservationResponse::from)
                                                     .toList();

        return new ReservationCollectionResponse(responses);
    }

    public record ReservationResponse(
            Long id,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int attendeeCount,
            OrganizerResponse organizer
    ) {

        private static ReservationResponse from(Reservation reservation) {
            OrganizerResponse organizerResponse = OrganizerResponse.from(reservation.getOrganizer());

            return new ReservationResponse(
                    reservation.getId().getValue(),
                    reservation.getStartTime(),
                    reservation.getEndTime(),
                    reservation.getAttendeeCount(),
                    organizerResponse
            );
        }
    }

    public record OrganizerResponse(String name, String phoneNumber, String password) {

        private static OrganizerResponse from(Organizer organizer) {
            return new OrganizerResponse(organizer.getName(), organizer.getPhoneNumber(), organizer.getPassword());
        }
    }
}
