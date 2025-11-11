package org.example.javareservationproject.domain.reservation;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

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

    // 반복
    private RepetitionType type;
    private int repeatCnt;

    // 시간
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public ReservationTime(LocalDate date, LocalTime startTime, LocalTime endTime, RepetitionType type, int repeatCnt) {
        validateOrder(startTime, endTime);
        validateRule(startTime, endTime);

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.repeatCnt = repeatCnt;
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

    // 이 예약이 실제로 발생하는 날짜 리스트
    private List<LocalDate> reservedDates() {
        if (type == null) {
            throw new InvalidReservationException("유효하지 않은 예약 반복 타입입니다.");
        }
        type.validate(repeatCnt);

        return switch (type) {
            case ONCE    -> List.of(date);
            case WEEKLY  -> IntStream.range(0, repeatCnt)
                .mapToObj(i -> date.plusWeeks(i))
                .toList();
            case MONTHLY -> IntStream.range(0, repeatCnt)
                .mapToObj(i -> date.plusMonths(i))
                .toList();
        };
    }

    // 입력 받은 예약과 충돌하는지 판단
    public boolean isConflict(ReservationTime other) {
        if (!isTimeOverlap(other.startTime, other.endTime)) {
            return false;
        }

        List<LocalDate> myDates = this.reservedDates();
        List<LocalDate> otherDates = other.reservedDates();

        int i = 0, j = 0;
        while (i < myDates.size() && j < otherDates.size()) {
            LocalDate a = myDates.get(i);
            LocalDate b = otherDates.get(j);

            if (a.isEqual(b)) {
                return true;
            }

            if (a.isBefore(b)) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }

    // 시간대 겹침 여부
    private boolean isTimeOverlap(LocalTime otherStart, LocalTime otherEnd) {
        return this.startTime.isBefore(otherEnd) && otherStart.isBefore(this.endTime);
    }
}
