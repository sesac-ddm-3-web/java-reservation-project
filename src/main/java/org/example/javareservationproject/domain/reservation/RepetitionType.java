package org.example.javareservationproject.domain.reservation;

import java.util.function.IntPredicate;

import org.example.javareservationproject.domain.reservation.exception.InvalidReservationException;

public enum RepetitionType {
    ONCE(cnt -> cnt == 1),
    WEEKLY(cnt -> cnt >= 2),
    MONTHLY(cnt -> cnt >= 2);

    private final IntPredicate validator;

    RepetitionType(IntPredicate validator) {
        this.validator = validator;
    }

    public void validate(int repeatCnt) {
        if (!validator.test(repeatCnt)) {
            throw new InvalidReservationException(
                "반복 타입 " + this.name() + "에 유효하지 않은 반복 횟수입니다. 입력된 반복 횟수: " + repeatCnt
            );
        }
    }
}
