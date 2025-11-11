package com.example.sesac_spring_practice_01.domain.reservation.exception;

import com.example.sesac_spring_practice_01.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReservationTimeNotValidException extends BaseException {
    public ReservationTimeNotValidException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "예약 시작/마감 시간을 다시 입력해주세요");
    }
}
