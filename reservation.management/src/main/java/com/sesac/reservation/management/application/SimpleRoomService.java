package com.sesac.reservation.management.application;

import com.sesac.reservation.management.domain.Room;
import com.sesac.reservation.management.infrastructure.ListRoomRepository;
import com.sesac.reservation.management.presentation.RoomDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleRoomService {
    private ListRoomRepository listRoomRepository;
    private ModelMapper modelMapper;

    @Autowired
    public SimpleRoomService(ListRoomRepository listRoomRepository, ModelMapper modelMapper) {
        this.listRoomRepository = listRoomRepository;
        this.modelMapper = modelMapper;
    }

    public List<RoomDto> showAllRooms() {
        List<Room> rooms = listRoomRepository.showAllRooms();

        List<RoomDto> roomDtos = rooms.stream()
                .map(room -> modelMapper.map(room, RoomDto.class))
                .toList();

        return roomDtos;
    }
}
