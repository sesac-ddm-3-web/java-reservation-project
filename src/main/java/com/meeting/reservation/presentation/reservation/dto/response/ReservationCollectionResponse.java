package com.meeting.reservation.presentation.reservation.dto.response;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.vo.EquipmentUsages;
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
            OrganizerResponse organizer,
            List<EquipmentUsageResponse> equipmentUsages
    ) {

        private static ReservationResponse from(Reservation reservation) {
            OrganizerResponse organizerResponse = OrganizerResponse.from(reservation.getOrganizer());
            List<EquipmentUsageResponse> equipmentusageResponses =
                    EquipmentUsageResponse.from(reservation.getEquipmentUsages());

            return new ReservationResponse(
                    reservation.getId().getValue(),
                    reservation.getTimeSlot().getStartTime(),
                    reservation.getTimeSlot().getEndTime(),
                    reservation.getAttendeeCount(),
                    organizerResponse,
                    equipmentusageResponses
            );
        }

        public record OrganizerResponse(String name, String phoneNumber, String password) {

            private static OrganizerResponse from(Organizer organizer) {
                return new OrganizerResponse(organizer.getName(), organizer.getPhoneNumber(), organizer.getPassword());
            }
        }

        public record EquipmentUsageResponse(Long id, String name, int quantity) {

            private static List<EquipmentUsageResponse> from(EquipmentUsages equipmentUsages) {
                return equipmentUsages.getEquipmentUsages()
                                      .values()
                                      .stream()
                                      .map(
                                              equipmentUsage -> new EquipmentUsageResponse(
                                                      equipmentUsage.getEquipmentId()
                                                                    .getId(),
                                                      equipmentUsage.getName(),
                                                      equipmentUsage.getQuantity()
                                              )
                                      )
                                      .toList();
            }
        }
    }
}
