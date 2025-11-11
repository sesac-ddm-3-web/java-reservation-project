package com.example.sesac_spring_practice_01.domain.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationCreateReqDto {

    @NotBlank(message = "예약자 이름은 필수입니다.")
    private String bookerName;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 4자리 숫자여야 합니다.")
    private String bookerPassword;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(
            regexp = "^010-\\d{4}-\\d{4}$",
            message = "전화번호는 010-xxxx-xxxx 형식이어야 합니다."
    )
    private String bookerPhone;

    @NotNull(message = "예약 시작 시간은 필수입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @NotNull(message = "예약 종료 시간은 필수입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;
}