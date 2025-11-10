package com.meeting.reservation.domain.reservation;

import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class Reservation {

    private final ReservationId id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Organizer organizer;

    public static Reservation create(Organizer organizer, LocalDateTime startTime, LocalDateTime endTime) {
        validateOrganizer(organizer);
        validateTime(startTime, endTime);

        return new Reservation(ReservationId.EMPTY_RESERVATION_ID, startTime, endTime, organizer);
    }

    private static void validateOrganizer(Organizer organizer) {
        if (organizer == null) {
            throw new IllegalArgumentException("예약자 정보는 비어 있을 수 없습니다.");
        }
    }

    private static void validateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("예약 시간 정보는 비어 있을 수 없습니다.");
        }

        long betweenSecond = ChronoUnit.SECONDS.between(startTime, endTime);

        if (betweenSecond <= 0L) {
            throw new IllegalArgumentException("예약 시작 시간은 예약 종료 시간보다 이전이어야 합니다.");
        }
    }

    private Reservation(ReservationId id, LocalDateTime startTime, LocalDateTime endTime, Organizer organizer) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
    }

    public Reservation withAssignedId(Long id) {
        ReservationId reservationId = ReservationId.create(id);

        return new Reservation(reservationId, this.startTime, this.endTime, this.organizer);
    }

    public boolean overlapTime(Reservation other) {
        return this.startTime.isBefore(other.endTime) && this.endTime.isAfter(other.startTime);
    }

    public boolean afterStartTime(LocalDateTime now) {
        return this.startTime.isBefore(now);
    }

    public boolean matchPassword(String password) {
        return organizer.matchPassword(password);
    }

    public boolean isEqualId(Long id) {
        return this.id.isEqualId(id);
    }
}
