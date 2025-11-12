package com.example.reservation.domain;

import jakarta.validation.constraints.*;

public class Reservation {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$")
    private String phoneNumber;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,8}$",
            message = "비밀번호는 영문과 숫자를 포함해 4~8자리로 입력하세요."
    )
    private String password;

    @Min(1)
    @Max(4)
    private Long roomId;

    @Min(7)
    @Max(21)
    private Integer startAt;

    @Min(8)
    @Max(22)
    private Integer endAt;

    public boolean sameRoomId(Long roomId) {
        return this.roomId == roomId;
    }

    public boolean sameId(Long id) {
        return this.id == id;
    }

    @AssertTrue(message = "종료 시간은 시작 시간보다 늦어야 합니다.")
    public boolean isEndAfterStart() {
        return endAt > startAt;
    }

    public String getPassword() {
        return password;
    }
}
