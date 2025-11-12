package com.meeting.reservation.domain.reservation.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ReservationId {

    public static final ReservationId EMPTY_RESERVATION_ID = new ReservationId(null);

    public static ReservationId create(Long value) {
        validateValue(value);

        return new ReservationId(value);
    }

    private static void validateValue(Long value) {
        if (value == null || value <= 0L) {
            throw new IllegalArgumentException("예약 ID는 양수여야 합니다.");
        }
    }

    private final Long value;

    private ReservationId(Long value) {
        this.value = value;
    }

    public boolean isEqualId(Long value) {
        return this.value.equals(value);
    }
}
