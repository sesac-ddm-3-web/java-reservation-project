package com.example.sesac_spring_practice_01.domain.room.exception;

import com.example.sesac_spring_practice_01.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class RoomNotFoundException extends BaseException {
    public RoomNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "회의실 " + id + "을 찾지 못했습니다.");
    }
}
