package com.sesac.reservation.management.infrastructure;

import com.sesac.reservation.management.domain.Room;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class ListRoomRepository {
    private List<Room> rooms = new CopyOnWriteArrayList<>();

    // 애플리케이션 시작 시 한 번만 호출
    @PostConstruct
    public void init() {
        rooms.add(new Room(1, "회의실 A", 4));
        rooms.add(new Room(2, "회의실 B", 6));
        rooms.add(new Room(3, "회의실 C", 8));
        rooms.add(new Room(4, "회의실 D", 12));
    }

    public List<Room> showAllRooms() {
        return rooms;
    }

    public Room findById(Integer id) {
        return rooms.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회의실입니다."));

        // 예외 처리: 존재하지 않는 회의실 ID로 예약하려는 경우
    }
}
