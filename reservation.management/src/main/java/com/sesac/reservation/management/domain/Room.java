package com.sesac.reservation.management.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Room {
    Integer id; // 미리 정의된 방 번호
    String name; // 예약자명
    Integer maxPool; // 최대 수용 인원

    // 시작 시각
    @JsonFormat(pattern = "HH:MM")
    LocalDateTime start;

    // 종료 시각
    @JsonFormat(pattern = "HH:MM")
    LocalDateTime end;

    // ListRoomRepository 에서, 미리 정의되는 방 구현을 위한 생성자 생성
    public Room(Integer id, String name, Integer maxPool, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.name = name;
        this.maxPool = maxPool;
        this.start = start;
        this.end = end;
    }
}
