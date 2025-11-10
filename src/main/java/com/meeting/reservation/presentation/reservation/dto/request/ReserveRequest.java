package com.meeting.reservation.presentation.reservation.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReserveRequest(
        @Future
        LocalDateTime startTime,

        @Future
        LocalDateTime endTime,

        @NotNull
        OrganizerRequest organizer
) {

    public record OrganizerRequest(
            @NotBlank
            String name,

            @NotBlank
            String phoneNumber,

            @NotBlank
            String password
    ) {
    }
}
