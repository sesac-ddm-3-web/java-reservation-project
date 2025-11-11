package com.sesac.reservation.management.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class Reservation {
    private Integer id; // 예약 번호 (auto increment FROM database)
    private Integer roomId; // 예약한 방 번호
    private String name; // 예약자명
    private String phoneNumber; // 전화번호
    private String password; // 비밀번호
    private Integer attendeeCount; // 참석 인원

    // 시작 시각
    @JsonFormat(pattern = "HH:mm")
    private LocalTime start;

    // 종료 시각
    @JsonFormat(pattern = "HH:mm")
    private LocalTime end;

    public Reservation() {
    }

    public Reservation(Integer roomId,
                       String name,
                       String phoneNumber,
                       String password,
                       Integer attendeeCount,
                       LocalTime start,
                       LocalTime end) {
        this.roomId = roomId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.attendeeCount = attendeeCount;
        this.start = start;
        this.end = end;
    }

    public Integer getId() {
        return id;
    }

    // 예약 번호 자동 증가를 위해
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(Integer attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
