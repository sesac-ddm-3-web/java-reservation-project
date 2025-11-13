package com.example.sesac_spring_practice_01.domain.room.repository;

import com.example.sesac_spring_practice_01.domain.room.Room;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface RoomRepository {
    Optional<Room> findById(Long id);
    List<Room> findAll();
}
