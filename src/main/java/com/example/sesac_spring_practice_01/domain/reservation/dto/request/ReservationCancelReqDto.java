package com.example.sesac_spring_practice_01.domain.reservation.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ReservationCancelReqDto {
    @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 4자리 숫자여야 합니다.")
    private String password;
}
