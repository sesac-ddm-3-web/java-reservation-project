package com.sesac.reservation.management.infrastructure;

import com.sesac.reservation.management.domain.Room;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class ListRoomRepository {
    private List<Room> rooms = new CopyOnWriteArrayList<>();

    // 애플리케이션 시작 시 한 번만 호출
    @PostConstruct
    public void init() {
        rooms.add(new Room(1, null, 4, null, null));
        rooms.add(new Room(2, null, 6, null, null));
        rooms.add(new Room(3, null, 8, null, null));
        rooms.add(new Room(4, null, 12, null, null));
    }

    public List<Room> showAllRooms() {
        return rooms;
    }
}
