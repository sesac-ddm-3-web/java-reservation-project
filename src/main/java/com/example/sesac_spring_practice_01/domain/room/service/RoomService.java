package com.example.sesac_spring_practice_01.domain.room.service;


import com.example.sesac_spring_practice_01.domain.room.Room;
import com.example.sesac_spring_practice_01.domain.room.dto.response.RoomResDto;
import com.example.sesac_spring_practice_01.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomResDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return RoomResDto.fromEntities(rooms);
    }
}
