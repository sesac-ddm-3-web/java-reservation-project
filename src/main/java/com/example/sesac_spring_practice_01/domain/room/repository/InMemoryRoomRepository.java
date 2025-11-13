package com.example.sesac_spring_practice_01.domain.room.repository;

import com.example.sesac_spring_practice_01.domain.room.Room;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryRoomRepository implements RoomRepository {

    private final Map<Long, Room> rooms = new HashMap<>();

    @Override
    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(rooms.get(id));
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(rooms.values());
    }

    public Room save(Room room) {
        return rooms.put(room.getId(), room);
    }
}
