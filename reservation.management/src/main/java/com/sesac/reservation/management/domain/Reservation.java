package com.sesac.reservation.management.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class Reservation {
    Integer id; // 예약 번호 (auto increment FROM database)
    Integer roomId; // 예약한 방 번호
    String name; // 예약자명
    String phoneNumber; // 전화번호
    String password; // 비밀번호
    Integer attendeeCount; // 참석 인원

    // 시작 시각
    @JsonFormat(pattern = "HH:mm")
    LocalTime start;

    // 종료 시각
    @JsonFormat(pattern = "HH:mm")
    LocalTime end;

    // 예약 번호 자동 증가를 위해
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }
}
