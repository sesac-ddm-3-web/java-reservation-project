package com.meeting.reservation.domain.room;

import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import com.meeting.reservation.domain.room.vo.MeetingRoomLocation;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class MeetingRoom {

    private final MeetingRoomId id;
    private final String name;
    private final int capacity;
    private final MeetingRoomLocation location;

    public static MeetingRoom create(String name, int capacity, MeetingRoomLocation location) {
        validateName(name);
        validateLocation(location);
        validateCapacity(capacity);

        return new MeetingRoom(MeetingRoomId.EMPTY_MEETING_ROOM_ID, name, capacity, location);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("회의실 이름은 비어 있을 수 없습니다.");
        }
    }

    private static void validateLocation(MeetingRoomLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("회의실 위치는 비어 있을 수 없습니다.");
        }
    }

    private static void validateCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("회의실 최대 수용 인원은 양수여야 합니다.");
        }
    }

    private MeetingRoom(MeetingRoomId id, String name, int capacity, MeetingRoomLocation location) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.location = location;
    }

    public MeetingRoom withAssignedId(Long id) {
        MeetingRoomId meetingRoomId = MeetingRoomId.create(id);

        return new MeetingRoom(
                meetingRoomId,
                this.name,
                this.capacity,
                this.location
        );
    }

    public MeetingRoom moveLocation(MeetingRoomLocation location) {
        return new MeetingRoom(
                this.id,
                this.name,
                this.capacity,
                location
        );
    }

    public MeetingRoom changeName(String changedName) {
        validateName(changedName);

        return new MeetingRoom(
                this.id,
                changedName,
                this.capacity,
                this.location
        );
    }

    public boolean canAccommodate(int attendeeCount) {
        return this.capacity >= attendeeCount;
    }

    public boolean isEqualId(Long id) {
        return this.id.isEqualId(id);
    }

    public boolean isEqualName(String name) {
        return this.name.equals(name);
    }

    public boolean isEqualLocation(int floor, int roomNumber) {
        return this.location.isEqualLocation(floor, roomNumber);
    }
}
