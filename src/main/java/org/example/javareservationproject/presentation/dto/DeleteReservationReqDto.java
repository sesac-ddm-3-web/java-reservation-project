package org.example.javareservationproject.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteReservationReqDto(
    @NotBlank String password
) {
}
