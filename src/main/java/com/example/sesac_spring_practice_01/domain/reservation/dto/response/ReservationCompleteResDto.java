package com.example.sesac_spring_practice_01.domain.reservation.dto.response;

import com.example.sesac_spring_practice_01.domain.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationCompleteResDto {
    private Long id;

    public static ReservationCompleteResDto from(Reservation reservation) {
        return new ReservationCompleteResDto(reservation.getId());
    }
}
