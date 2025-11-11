package com.sesac.reservation.management.presentation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class ReservationDto {
    Integer id; // 예약 번호 (auto increment FROM database)
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAttendeeCount() {
        return attendeeCount;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
