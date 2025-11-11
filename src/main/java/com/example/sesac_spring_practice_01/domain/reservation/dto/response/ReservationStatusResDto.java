package com.example.sesac_spring_practice_01.domain.reservation.dto.response;

import com.example.sesac_spring_practice_01.domain.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationStatusResDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    public static ReservationStatusResDto from(Reservation reservation){
        return new ReservationStatusResDto(reservation.getId(), reservation.getStartAt(), reservation.getEndAt());
    }

    public static List<ReservationStatusResDto> fromEntities(List<Reservation> reservations){
        return reservations.stream().map(ReservationStatusResDto::from).toList();
    }
}
