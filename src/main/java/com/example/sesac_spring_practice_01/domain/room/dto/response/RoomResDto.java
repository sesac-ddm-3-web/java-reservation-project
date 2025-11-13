package com.example.sesac_spring_practice_01.domain.room.dto.response;

import com.example.sesac_spring_practice_01.domain.room.Room;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomResDto {
    private Long id;
    private String name;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    public static RoomResDto from(Room room) {
        return new RoomResDto(room.getId(), room.getName(), room.getOpenAt(), room.getCloseAt());
    }

    public static List<RoomResDto> fromEntities(List<Room> rooms) {
        return rooms.stream()
                .map(RoomResDto::from)
                .toList();
    }
}
