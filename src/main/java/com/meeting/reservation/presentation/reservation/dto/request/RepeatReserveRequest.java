package com.meeting.reservation.presentation.reservation.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record RepeatReserveRequest(
        @Future
        LocalDateTime startTime,

        @Future
        LocalDateTime endTime,

        @NotNull
        OrganizerRequest organizer,

        @Positive
        int attendeeCount,

        @NotBlank
        String frequency,

        @Positive
        int repeatCount
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
