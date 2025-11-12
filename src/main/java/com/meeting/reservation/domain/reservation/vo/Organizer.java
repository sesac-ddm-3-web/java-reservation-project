package com.meeting.reservation.domain.reservation.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Organizer {

    private static final String PHONE_REGEX = "^01(?:0|1|[6-9])-\\d{3,4}-\\d{4}$";

    private final String name;
    private final String phoneNumber;
    private final String password;

    public static Organizer create(String name, String phoneNumber, String password) {
        validateName(name);
        validatePhoneNumber(phoneNumber);
        validatePassword(password);

        return new Organizer(name, phoneNumber, password);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자 명은 비어 있을 수 없습니다.");
        }
    }

    private static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("전화번호 양식은 하이픈을 포함해 핸드폰 양식으로 작성해야 합니다. (010-0000-0000)");
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 비어 있을 수 없습니다.");
        }
    }

    private Organizer(String name, String phoneNumber, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
