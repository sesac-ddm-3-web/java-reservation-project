package com.example.sesac_spring_practice_01.domain.room.exception;

import com.example.sesac_spring_practice_01.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class RoomReservationTimeOutOfRangeException extends BaseException {
    public RoomReservationTimeOutOfRangeException(Long id) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, id + " 회의실의 예약 가능 시간 범위를 벗어났습니다.");
    }
}
