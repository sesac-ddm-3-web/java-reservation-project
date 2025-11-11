package org.example.javareservationproject.domain.reservation.exception;

import org.example.javareservationproject.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends BusinessException {

    public ReservationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당하는 ID에 맞는 예약을 찾지 못했습니다.");
    }
}
