package spring_junyeong.__meetingRoom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring_junyeong.__meetingRoom.domain.dto.MeetingRoomResponseDto;
import spring_junyeong.__meetingRoom.service.MeetingRoomService;

import java.util.List;

@RestController
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @Autowired
    MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    // 미팅룸 - 전체 조회
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public List<MeetingRoomResponseDto> getMeetingRoomList(){
        return meetingRoomService.getMeetingRoomList();
    }

}
