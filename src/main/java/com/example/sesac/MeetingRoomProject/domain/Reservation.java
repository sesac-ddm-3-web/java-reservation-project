package com.example.sesac.MeetingRoomProject.domain;

import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    private Long roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String name;
    private String phoneNumber;
    private String password;

    public Boolean sameId(Long id) {
        return this.id.equals(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomId(Long roomId){
        this.roomId = roomId;
    }

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

    public boolean isOverlapped(Reservation reservation) {
        return this.startTime.isBefore(reservation.endTime) &&
                this.endTime.isAfter(reservation.startTime);
    }
}
