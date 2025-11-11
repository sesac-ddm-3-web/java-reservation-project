package com.example.assignmant.service;

import com.example.assignmant.dto.ReservationDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
@Service
public class ReservationValidator {
    private final static String NAME_RULE = "^[a-zA-Z가-힣0-9]*$";
    private final static String PHONE_RULE = "^010-\\d{4}-\\d{4}$";

    public void validate(ReservationDto reservationDto) {
        validateName(reservationDto.getName());
        validatePhoneNumber(reservationDto.getPhoneNumber());
        validateDateTime(reservationDto.getStartTime(),reservationDto.getEndTime());
    }

    private void validateName(String name) {
        if(!name.matches(NAME_RULE)) {
            throw new IllegalArgumentException("이름은 숫자와 한글만 가능합니다.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches(PHONE_RULE)) {
            throw new IllegalArgumentException("핸드폰 형식을 맞춰주세요. 010 - XXXX - XXXX");
        }
    }


    private void validateDateTime(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        validateStartBeforeEnd(startTime, endTime);
        validateNotPastTime(startTime);
    }

    private static void validateNotPastTime(LocalDateTime startTime) {
        if(startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("과거 시간으로 예약할 수 없습니다.");
        }
    }

    private static void validateStartBeforeEnd(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if(startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }
    }
}
