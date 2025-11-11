package com.sesac.reservation.management.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Room {
    private Integer id; // 미리 정의된 방 번호
    private String name; // 회의실 이름
    private Integer maxPool; // 최대 수용 인원

    // ListRoomRepository 에서, 미리 정의되는 방 구현을 위한 생성자 생성
    public Room(Integer id, String name, Integer maxPool) {
        this.id = id;
        this.name = name;
        this.maxPool = maxPool;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxPool() {
        return maxPool;
    }
}
