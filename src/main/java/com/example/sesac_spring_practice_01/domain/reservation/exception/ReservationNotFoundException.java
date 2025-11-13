package com.example.sesac_spring_practice_01.domain.reservation.exception;

import com.example.sesac_spring_practice_01.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends BaseException {
    public ReservationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "예약 확인이 되지 않았습니다. 예약 정보를 다시 입력해주세요");
    }
}
