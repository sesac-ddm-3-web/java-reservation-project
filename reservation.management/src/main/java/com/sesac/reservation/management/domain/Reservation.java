package com.sesac.reservation.management.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Reservation {
    Integer id; // 예약 번호 (auto increment FROM database)
    String name; // 예약자명
    String phoneNumber; // 전화번호
    String password; // 비밀번호
    Integer attendeeCount; // 참석 인원

    // 시작 시각
    @JsonFormat(pattern = "HH:MM")
    LocalDateTime start;

    // 종료 시각
    @JsonFormat(pattern = "HH:MM")
    LocalDateTime end;
}
