package com.meeting.reservation.presentation.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CancelReservationRequest(
        @NotBlank
        String password
) {
}
