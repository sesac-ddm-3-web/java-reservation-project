package org.example.javareservationproject.domain.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationInfo {
    private String client;
    private String phoneNumber;
    private String password;

    public boolean isPasswordMatches(String password) {
        return this.password.equals(password);
    }
}
