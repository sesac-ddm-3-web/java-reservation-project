package com.meeting.reservation.domain.room.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MeetingRoomId {

    public static final MeetingRoomId EMPTY_MEETING_ROOM_ID = new MeetingRoomId(null);

    public static MeetingRoomId create(Long value) {
        validateValue(value);

        return new MeetingRoomId(value);
    }

    private static void validateValue(Long value) {
        if (value == null || value <= 0L) {
            throw new IllegalArgumentException("회의실 ID는 양수여야 합니다.");
        }
    }

    private final Long value;

    private MeetingRoomId(Long value) {
        this.value = value;
    }

    public boolean isEqualId(Long value) {
        return this.value.equals(value);
    }
}
