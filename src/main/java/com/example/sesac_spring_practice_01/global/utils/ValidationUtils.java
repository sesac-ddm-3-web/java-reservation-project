package com.example.sesac_spring_practice_01.global.utils;

import com.example.sesac_spring_practice_01.domain.reservation.exception.ReservationInvalidFieldException;
import com.example.sesac_spring_practice_01.domain.reservation.exception.ReservationTimeNotValidException;
import com.example.sesac_spring_practice_01.domain.room.exception.RoomInvalidFieldException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Predicate;

public class ValidationUtils {

    public static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    public static <T> void requireNonNull(T value, String message) {
        if (value == null) {
            throw new ReservationInvalidFieldException(message);
        }
    }

    public static void requireTrue(boolean condition, String message) {
        if (!condition) {
            throw new ReservationInvalidFieldException(message);
        }
    }

    public static void requirePattern(String value, String regex, String message) {
        if (value == null || !value.matches(regex)) {
            throw new ReservationInvalidFieldException(message);
        }
    }

    public static <T> void validate(T value, Predicate<T> predicate, String message) {
        if (!predicate.test(value)) {
            throw new ReservationInvalidFieldException(message);
        }
    }

    public static void validateReservationFields(Long roomId,
                                                 LocalDateTime startAt,
                                                 LocalDateTime endAt,
                                                 String name,
                                                 String phone,
                                                 String bookerPassword) {
        requireNonNull(roomId, "roomId는 필수입니다.");
        requireNonNull(startAt, "예약 시작 시간은 필수입니다.");
        requireNonNull(endAt, "예약 종료 시간은 필수입니다.");
        if (!startAt.isBefore(endAt)) {
            throw new ReservationTimeNotValidException();
        }
        validate(name, n -> n != null && !n.isBlank(), "예약자 이름은 필수입니다.");
        validate(bookerPassword, p -> p != null && p.length() == 4 && p.chars().allMatch(Character::isDigit),
                "비밀번호는 4자리 숫자여야 합니다.");
        requirePattern(phone, "^010-\\d{4}-\\d{4}$", "전화번호는 010-xxxx-xxxx 형식이어야 합니다.");
    }

    public static void validateRoomFields(String name, LocalTime openAt, LocalTime closeAt) {
        if (name == null || name.isBlank()) {
            throw new RoomInvalidFieldException("회의실 이름은 필수입니다.");
        }
        if (openAt == null || closeAt == null) {
            throw new RoomInvalidFieldException("회의실 운영 시간은 필수입니다.");
        }
        LocalTime o = TimeUtils.snapToMinute(openAt);
        LocalTime c = TimeUtils.snapToMinute(closeAt);
        if (!o.isBefore(c)) {
            throw new RoomInvalidFieldException("회의실 시작/종료 시간이 유효하지 않습니다.");
        }
    }
}
