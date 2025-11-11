package com.meeting.reservation.domain.reservation.vo;

import com.meeting.reservation.domain.reservation.ReservationFrequency;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Getter;

@Getter
public class TimeSlot {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public static TimeSlot create(LocalDateTime startTime, LocalDateTime endTime) {
        validateTime(startTime, endTime);

        return new TimeSlot(startTime, endTime);
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

    private TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeSlot shiftBy(ReservationFrequency frequency, long amount) {
        return new TimeSlot(
                frequency.addTo(this.startTime, amount),
                frequency.addTo(this.endTime, amount)
        );
    }

    public boolean overlapTime(TimeSlot other) {
        return this.startTime.isBefore(other.endTime) && this.endTime.isAfter(other.startTime);
    }

    public boolean afterStartTime(LocalDateTime now) {
        return this.startTime.isBefore(now);
    }
}
