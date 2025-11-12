package com.example.sesac.MeetingRoomProject.presentation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReservationDto {
    private Long id;
    private Long roomId;

    @NotNull(message = "시작 시간은 필수입니다.")
    @Future(message = "시작 시간은 현재 시간 이후여야 합니다.")
    private LocalDateTime startTime;

    @NotNull(message = "종료 시간은 필수입니다.")
    @Future(message = "종료 시간은 현재 시간 이후여야 합니다.")
    private LocalDateTime endTime;

    @NotBlank(message = "예약자 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "예약자 연락처는 필수입니다.")
    private String phoneNumber;

    @NotBlank(message = "예약자 비밀번호는 필수입니다.")
    private String password;

    public Long getId() {
        return id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
