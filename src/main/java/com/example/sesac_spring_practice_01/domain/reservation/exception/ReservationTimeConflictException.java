package com.example.sesac_spring_practice_01.domain.reservation.exception;

import com.example.sesac_spring_practice_01.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReservationTimeConflictException extends BaseException {
    public ReservationTimeConflictException() {
        super(HttpStatus.CONFLICT, "이미 예약된 시간대입니다.");
    }
}
