package com.meeting.reservation.domain.room.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MeetingRoomLocation {

    private final int floor;
    private final int roomNumber;

    public static MeetingRoomLocation create(int floor, int roomNumber) {
        validateRoomNumber(roomNumber);

        return new MeetingRoomLocation(floor, roomNumber);
    }

    private static void validateRoomNumber(int roomNumber) {
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("회의실 방 번호는 양수여야 합니다.");
        }
    }

    private MeetingRoomLocation(int floor, int roomNumber) {
        this.floor = floor;
        this.roomNumber = roomNumber;
    }

    public boolean isEqualLocation(int floor, int roomNumber) {
        return this.floor == floor && this.roomNumber == roomNumber;
    }
}
