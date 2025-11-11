package com.meeting.reservation.domain.reservation;

import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import com.meeting.reservation.domain.reservation.vo.TimeSlot;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class Reservation {

    private final ReservationId id;
    private final MeetingRoomId meetingRoomId;
    private final TimeSlot timeSlot;
    private final int attendeeCount;
    private final Organizer organizer;

    public static Reservation create(
            MeetingRoomId meetingRoomId,
            Organizer organizer,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int attendeeCount
    ) {
        validateOrganizer(organizer);
        validateAttendeeCount(attendeeCount);

        return new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                meetingRoomId,
                TimeSlot.create(startTime, endTime),
                attendeeCount,
                organizer
        );
    }

    private static void validateOrganizer(Organizer organizer) {
        if (organizer == null) {
            throw new IllegalArgumentException("예약자 정보는 비어 있을 수 없습니다.");
        }
    }

    private static void validateAttendeeCount(int attendeeCount) {
        if (attendeeCount <= 0) {
            throw new IllegalArgumentException("참가 인원은 양수여야 합니다.");
        }
    }

    Reservation(
            ReservationId id,
            MeetingRoomId meetingRoomId,
            TimeSlot timeSlot,
            int attendeeCount,
            Organizer organizer
    ) {
        this.id = id;
        this.meetingRoomId = meetingRoomId;
        this.timeSlot = timeSlot;
        this.attendeeCount = attendeeCount;
        this.organizer = organizer;
    }

    public Reservation withAssignedId(Long id) {
        ReservationId reservationId = ReservationId.create(id);

        return new Reservation(
                reservationId,
                this.meetingRoomId,
                this.timeSlot,
                this.attendeeCount,
                this.organizer
        );
    }

    public Reservation shift(ReservationFrequency frequency) {
        TimeSlot shiftTimeSlot = this.timeSlot.shiftBy(frequency);

        return new Reservation(
                this.id,
                this.meetingRoomId,
                shiftTimeSlot,
                this.attendeeCount,
                this.organizer
        );
    }

    public boolean overlapTime(Reservation other) {
        return timeSlot.overlapTime(other.timeSlot);
    }

    public boolean afterStartTime(LocalDateTime now) {
        return timeSlot.afterStartTime(now);
    }

    public boolean matchPassword(String password) {
        return organizer.matchPassword(password);
    }

    public boolean isEqualId(Long id) {
        return this.id.isEqualId(id);
    }
}
