package org.example.javareservationproject.domain.reservation.exception;

import org.example.javareservationproject.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidReservationException extends BusinessException {

    public InvalidReservationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
