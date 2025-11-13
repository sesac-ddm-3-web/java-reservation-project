package com.example.sesac_spring_practice_01.domain.reservation.exception;

import com.example.sesac_spring_practice_01.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReservationInvalidFieldException extends BaseException {
    public ReservationInvalidFieldException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
