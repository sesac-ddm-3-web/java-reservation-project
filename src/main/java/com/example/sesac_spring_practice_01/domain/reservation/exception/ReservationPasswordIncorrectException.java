package com.example.sesac_spring_practice_01.domain.reservation.exception;

import com.example.sesac_spring_practice_01.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReservationPasswordIncorrectException extends BaseException {
    public ReservationPasswordIncorrectException() {
        super(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다");
    }
}
