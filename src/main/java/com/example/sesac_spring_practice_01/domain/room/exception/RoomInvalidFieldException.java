package com.example.sesac_spring_practice_01.domain.room.exception;

import com.example.sesac_spring_practice_01.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class RoomInvalidFieldException extends BaseException {
    public RoomInvalidFieldException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
