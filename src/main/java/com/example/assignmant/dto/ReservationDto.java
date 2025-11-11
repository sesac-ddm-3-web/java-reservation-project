package com.example.assignmant.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ReservationDto {
    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String password;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private Long roomId;

    public ReservationDto() {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
