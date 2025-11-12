package com.example.assignmant.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationDto {
    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String password;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private Long roomId;

    @NotNull
    @Min(1)
    private Integer capacity;

}
