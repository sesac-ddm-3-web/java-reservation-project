package com.example.sesac_spring_practice_01.domain.reservation;

import com.example.sesac_spring_practice_01.domain.reservation.exception.ReservationPasswordIncorrectException;
import com.example.sesac_spring_practice_01.global.ReservationIds;
import com.example.sesac_spring_practice_01.global.utils.ValidationUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    private Long id;
    private Long roomId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String bookerName;
    private String bookerPhone;
    private String bookerPassword;

    private Reservation(Long roomId,
                        LocalDateTime startAt,
                        LocalDateTime endAt,
                        String name,
                        String phone,
                        String bookerPassword) {
        this.id = ReservationIds.nextId();
        this.roomId = roomId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.bookerName = name;
        this.bookerPhone = phone;
        this.bookerPassword = bookerPassword;
    }

    public void checkPassword(String password) {
        if (!this.bookerPassword.equals(password)) {
            throw new ReservationPasswordIncorrectException();
        }
    }

    public boolean overlaps(LocalDateTime startAt, LocalDateTime endAt) {
        return this.startAt.isBefore(endAt) && startAt.isBefore(this.endAt);
    }

    public boolean sameRoomId(Long roomId) {
        return this.roomId.equals(roomId);
    }

    public static Reservation create(Long roomId,
                                     LocalDateTime startAt,
                                     LocalDateTime endAt,
                                     String name,
                                     String phone,
                                     String bookerPassword) {
        ValidationUtils.validateReservationFields(roomId, startAt, endAt, name, phone, bookerPassword);
        return new Reservation(roomId, startAt, endAt, name, phone, bookerPassword);
    }

}