package com.sesac.reservation.management.presentation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class RoomDto {
    Integer id; // 미리 정의된 방 번호
    String name; // 예약자명
    Integer maxPool; // 최대 수용 인원

    // 시작 시각
    @JsonFormat(pattern = "HH:MM")
    LocalDateTime start;

    // 종료 시각
    @JsonFormat(pattern = "HH:MM")
    LocalDateTime end;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxPool() {
        return maxPool;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
