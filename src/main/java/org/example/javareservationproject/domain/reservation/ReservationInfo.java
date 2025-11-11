package org.example.javareservationproject.domain.reservation;

import org.example.javareservationproject.domain.reservation.exception.InvalidReservationException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationInfo {
    private String client;
    private String phoneNumber;
    private int headcount;
    private String password;

    public ReservationInfo(int headcount, String client, String password, String phoneNumber) {
        validateHeadCount(headcount);

        this.client = client;
        this.headcount = headcount;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void validateHeadCount(int headcount) {
        if (headcount < 1) {
            throw new InvalidReservationException("예약 인원은 1보다 작을 수 없습니다.");
        }
    }

    public boolean isPasswordMatches(String password) {
        return this.password.equals(password);
    }
}
