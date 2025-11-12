package com.jiwoo.MeetingRoom.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    @NotNull
    private Long RoomId;
    @NotNull
    private LocalDateTime startAt;
    @NotNull
    private LocalDateTime endAt;

    @Valid
    @NotNull
    private GuestInfo guest;

    public Reservation(Long roomId, LocalDateTime startAt, LocalDateTime endAt, GuestInfo guest) {
        RoomId = roomId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.guest = guest;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getRoomId() {
        return RoomId;
    }


    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public GuestInfo getGuest() {
        return guest;
    }
}
