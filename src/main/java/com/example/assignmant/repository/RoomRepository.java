package com.example.assignmant.repository;

import com.example.assignmant.domain.Room;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RoomRepository {
    private final Map<Long, Room > store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    public List<Room> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @PostConstruct
    public void init() {
        for (int i = 101; i <= 105; i++) {
            Room room = new Room();
            room.setId(sequence.getAndIncrement());
            room.setRoomNumber(i);
            store.put(room.getId(), room);
        }
    }
}
