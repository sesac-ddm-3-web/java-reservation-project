package com.example.sesac.MeetingRoomProject.application;

import com.example.sesac.MeetingRoomProject.domain.MeetingRoom;
import com.example.sesac.MeetingRoomProject.infrastructure.ListMeetingRoomRepository;
import com.example.sesac.MeetingRoomProject.presentation.MeetingRoomDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingRoomService {
    private final ListMeetingRoomRepository listMeetingRoomRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MeetingRoomService(ListMeetingRoomRepository listMeetingRoomRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.listMeetingRoomRepository = listMeetingRoomRepository;
    }

    public List<MeetingRoomDto> getMeetingRooms(){
        List<MeetingRoom> meetingRooms = listMeetingRoomRepository.getMeetingRooms();
        List<MeetingRoomDto> meetingRoomDtos =meetingRooms.stream()
                .map(meetingRoom -> modelMapper.map(meetingRoom, MeetingRoomDto.class))
                .toList();
        return meetingRoomDtos;
    }

}
