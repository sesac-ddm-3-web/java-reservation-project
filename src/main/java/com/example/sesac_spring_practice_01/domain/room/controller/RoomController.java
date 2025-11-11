package com.example.sesac_spring_practice_01.domain.room.controller;

import com.example.sesac_spring_practice_01.domain.room.dto.response.RoomResDto;
import com.example.sesac_spring_practice_01.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomResDto> getAllRooms() {
        return roomService.getAllRooms();
    }
}
