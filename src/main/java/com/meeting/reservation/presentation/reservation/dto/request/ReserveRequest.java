package com.meeting.reservation.presentation.reservation.dto.request;

import com.meeting.reservation.application.dto.request.EquipmentUsageDto;
import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.TimeSlot;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ReserveRequest(
        @Future
        LocalDateTime startTime,

        @Future
        LocalDateTime endTime,

        @NotNull
        OrganizerRequest organizer,

        @Positive
        int attendeeCount,

        List<EquipmentUsageRequest> equipmentUsages
) {

    public ReserveRequest {
        if (equipmentUsages == null) {
            equipmentUsages = Collections.emptyList();
        }
    }

    public record OrganizerRequest(
            @NotBlank
            String name,

            @NotBlank
            String phoneNumber,

            @NotBlank
            String password
    ) {
    }

    public record EquipmentUsageRequest(Long equipmentId, int quantity) {
    }

    public TimeSlot toTimeSlot() {
        return TimeSlot.create(this.startTime, this.endTime);
    }

    public Organizer toOrganizer() {
        return Organizer.create(
                this.organizer.name,
                this.organizer.phoneNumber,
                this.organizer.password
        );
    }

    public List<EquipmentUsageDto> toEquipmentUsages() {
        return this.equipmentUsages.stream()
                                   .map(request ->
                                           new EquipmentUsageDto(
                                                   EquipmentId.create(request.equipmentId),
                                                   request.quantity()
                                           )
                                   )
                                   .toList();
    }
}
