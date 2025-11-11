package com.example.sesac_spring_practice_01.domain.room;

import com.example.sesac_spring_practice_01.domain.room.exception.RoomInvalidFieldException;
import com.example.sesac_spring_practice_01.domain.room.exception.RoomReservationTimeOutOfRangeException;
import com.example.sesac_spring_practice_01.global.RoomIds;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static com.example.sesac_spring_practice_01.global.utils.TimeUtils.snapToMinute;
import static com.example.sesac_spring_practice_01.global.utils.ValidationUtils.isBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    private Long id;
    private String name;
    private LocalTime openAt;
    private LocalTime closeAt;

    private Room(String name, LocalTime openAt, LocalTime closeAt) {
        if (isBlank(name)) {
            throw new RoomInvalidFieldException("회의실 이름은 필수입니다.");
        }
        if (openAt == null || closeAt == null) {
            throw new RoomInvalidFieldException("회의실 운영 시간은 필수입니다.");
        }
        if (!openAt.isBefore(closeAt)) {
            throw new RoomInvalidFieldException("회의실 시작/종료 시간이 유효하지 않습니다.");
        }
        LocalTime snappedOpenAt = snapToMinute(openAt);
        LocalTime snappedCloseAt = snapToMinute(closeAt);
        if (!snappedOpenAt.isBefore(snappedCloseAt)) {
            throw new RoomInvalidFieldException("회의실 시작/종료 시간이 유효하지 않습니다.");
        }
        this.id = RoomIds.nextId();
        this.name = name;
        this.openAt = snappedOpenAt;
        this.closeAt = snappedCloseAt;
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
        return new Room(name, openAt, closeAt);
    }
}