package spring_junyeong.__meetingRoom.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_junyeong.__meetingRoom.domain.MeetingRoom;
import spring_junyeong.__meetingRoom.domain.dto.MeetingRoomResponseDto;
import spring_junyeong.__meetingRoom.repository.MeetingRoomRepository;

import java.util.List;

@Service
public class MeetingRoomService {
    private MeetingRoomRepository meetingRoomRepository;
    private ModelMapper modelMapper;

    @Autowired
    MeetingRoomService(MeetingRoomRepository meetingRoomRepository, ModelMapper modelMapper){
        this.meetingRoomRepository = meetingRoomRepository;
        this.modelMapper = modelMapper;
    }

    public List<MeetingRoomResponseDto> getMeetingRoomList() {
        List<MeetingRoom> meetingRoomList =  meetingRoomRepository.getMeetingRoomList();
        return meetingRoomList.stream().map(room -> modelMapper.map(room, MeetingRoomResponseDto.class)).toList();
    }
}
