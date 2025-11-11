package com.example.sesac_spring_practice_01.domain.reservation;

import com.example.sesac_spring_practice_01.domain.reservation.exception.ReservationInvalidFieldException;
import com.example.sesac_spring_practice_01.domain.reservation.exception.ReservationPasswordIncorrectException;
import com.example.sesac_spring_practice_01.domain.reservation.exception.ReservationTimeNotValidException;
import com.example.sesac_spring_practice_01.global.ReservationIds;
import com.example.sesac_spring_practice_01.global.utils.TimeUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.example.sesac_spring_practice_01.global.utils.ValidationUtils.isBlank;


@Getter
@AllArgsConstructor
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

        if (roomId == null) {
            throw new ReservationInvalidFieldException("roomId는 필수입니다.");
        }
        if (startAt == null || endAt == null) {
            throw new ReservationInvalidFieldException("예약 시간은 필수입니다.");
        }

        LocalDateTime snappedStartAt = TimeUtils.snapToMinute(startAt);
        LocalDateTime snappedEndAt = TimeUtils.snapToMinute(endAt);

        if (!snappedStartAt.isBefore(snappedEndAt)) {
            throw new ReservationTimeNotValidException();
        }

        if (isBlank(name)) {
            throw new ReservationInvalidFieldException("예약자 이름은 필수입니다.");
        }

        if (isBlank(bookerPassword) || bookerPassword.length() != 4 ||
                !bookerPassword.chars().allMatch(Character::isDigit)) {
            throw new ReservationInvalidFieldException("비밀번호는 4자리 숫자여야 합니다.");
        }

        if (isBlank(phone) || !phone.matches("^010-\\d{4}-\\d{4}$")) {
            throw new ReservationInvalidFieldException("전화번호는 010-xxxx-xxxx 형식이어야 합니다.");
        }

        this.id = ReservationIds.nextId();
        this.roomId = roomId;
        this.startAt = snappedStartAt;
        this.endAt = snappedEndAt;
        this.bookerName = name;
        this.bookerPhone = phone;
        this.bookerPassword = bookerPassword;
    }

    public void validatePassword(String password) {
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
        return new Reservation(roomId, startAt, endAt, name, phone, bookerPassword);
    }

}