package org.example.javareservationproject.domain.reservation.exception;

import org.example.javareservationproject.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ReservationTimeConflictException extends BusinessException {

    public ReservationTimeConflictException() {
        super(HttpStatus.CONFLICT, "이미 예약된 시간에 예약할 수 없습니다.");
    }
}
