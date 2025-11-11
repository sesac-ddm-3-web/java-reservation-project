package com.meeting.reservation.domain.reservation;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.BiFunction;

public enum ReservationFrequency {
    DAILY(LocalDateTime::plusDays),
    WEEKLY(LocalDateTime::plusWeeks),
    MONTHLY(LocalDateTime::plusMonths),
    YEARLY(LocalDateTime::plusYears);

    private final BiFunction<LocalDateTime, Long, LocalDateTime> dateAdder;

    public static ReservationFrequency find(String name) {
        return Arrays.stream(ReservationFrequency.values())
                     .filter(reservationFrequency -> reservationFrequency.name().equalsIgnoreCase(name))
                     .findAny()
                     .orElseThrow(() -> new IllegalArgumentException("지정한 예약 주기를 찾을 수 없습니다."));
    }

    ReservationFrequency(BiFunction<LocalDateTime, Long, LocalDateTime> dateAdder) {
        this.dateAdder = dateAdder;
    }

    public LocalDateTime addTo(LocalDateTime dateTime, long amount) {
        return this.dateAdder.apply(dateTime, amount);
    }
}
