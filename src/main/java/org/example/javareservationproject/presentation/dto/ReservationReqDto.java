package org.example.javareservationproject.presentation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.example.javareservationproject.domain.reservation.RepetitionType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReservationReqDto(
    @NotBlank String client,

    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호는 000-0000-0000 형식이어야 합니다.")
    @NotBlank
    String phoneNumber,

    @NotBlank
    @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 4자리 숫자여야 합니다.")
    String password,

    @NotNull RepetitionType repetitionType,
    @Min(1) @Max(12) int repeatCnt,

    @NotNull LocalDate date,
    @NotNull LocalTime startTime,
    @NotNull LocalTime endTime,

    @Min(1) int headcount
) {}
