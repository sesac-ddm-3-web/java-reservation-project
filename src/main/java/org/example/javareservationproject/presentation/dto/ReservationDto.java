package org.example.javareservationproject.presentation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.example.javareservationproject.domain.reservation.Reservation;
import org.example.javareservationproject.domain.reservation.ReservationInfo;
import org.example.javareservationproject.domain.reservation.ReservationTime;

public record ReservationDto(
    long id,
    String client,
    String phoneNumber,
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime,
    LocalDateTime reservedAt
) {

    public static ReservationDto toDto(Reservation reservation) {
        ReservationInfo info = reservation.getInfo();
        ReservationTime time = reservation.getTime();

        return new ReservationDto(
            reservation.getId(),
            info.getClient(),
            info.getPhoneNumber(),
            time.getDate(),
            time.getStartTime(),
            time.getEndTime(),
            reservation.getReservedAt()
        );
    }
}
