package com.example.sesac_spring_practice_01.domain.room;

import com.example.sesac_spring_practice_01.domain.room.exception.RoomReservationTimeOutOfRangeException;
import com.example.sesac_spring_practice_01.global.RoomIds;
import com.example.sesac_spring_practice_01.global.utils.ValidationUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    private Long id;
    private String name;
    private LocalTime openAt;
    private LocalTime closeAt;

    private Room(String name, LocalTime openAt, LocalTime closeAt) {
        this.id = RoomIds.nextId();
        this.name = name;
        this.openAt = openAt;
        this.closeAt = closeAt;
    }

    public void validateReservationAllowed(LocalTime reservationStartAt, LocalTime reservationEndAt) {
        if (reservationStartAt == null || reservationEndAt == null) {
            throw new RoomReservationTimeOutOfRangeException(this.id);
        }
        boolean outOfRange =
                reservationStartAt.isBefore(this.openAt) || reservationEndAt.isAfter(this.closeAt);
        if (outOfRange) {
            throw new RoomReservationTimeOutOfRangeException(this.id);
        }
    }

    public static Room create(String name, LocalTime openAt, LocalTime closeAt) {
        ValidationUtils.validateRoomFields(name, openAt, closeAt);
        return new Room(name, openAt, closeAt);
    }
}