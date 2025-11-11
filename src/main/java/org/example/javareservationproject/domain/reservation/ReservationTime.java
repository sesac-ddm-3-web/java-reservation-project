package org.example.javareservationproject.domain.reservation;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.example.javareservationproject.domain.reservation.exception.InvalidReservationException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationTime {
    // 영업 시간
    private static final LocalTime AVAILABLE_START_TIME = LocalTime.of(9, 0, 0);
    private static final LocalTime AVAILABLE_END_TIME = LocalTime.of(23, 0, 0);
    private static final int MAX_MINUTES = 6 * 60; // 6시간

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public ReservationTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        validateOrder(startTime, endTime);
        validateRule(startTime, endTime);

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void validateOrder(LocalTime startTime, LocalTime endTime) {
        if (startTime.equals(endTime)) {
            throw new InvalidReservationException("시작 시간은 종료 시간과 같을 수 없습니다.");
        }

        if (startTime.isAfter(endTime)) {
            throw new InvalidReservationException("시작 시간은 종료 시간보다 나중일 수 없습니다.");
        }
    }

    public void validateRule(LocalTime startTime, LocalTime endTime) {
        long minutes = Duration.between(startTime, endTime).toMinutes();

        if (minutes > MAX_MINUTES) {
            throw new InvalidReservationException("예약 시간은 최대 6시간을 넘을 수 없습니다.");
        }

        if (startTime.isBefore(AVAILABLE_START_TIME) || endTime.isAfter(AVAILABLE_END_TIME)) {
            throw new InvalidReservationException("오전 9시에서 오후 11시 사이에만 예약 가능합니다.");
        }
    }

    public boolean isConflict(LocalDate date, LocalTime inputStartTime, LocalTime inputEndTime) {
        if (!this.date.equals(date)) {
            return false;
        }
        return inputStartTime.isBefore(this.endTime) && inputEndTime.isAfter(this.startTime);
    }
}
