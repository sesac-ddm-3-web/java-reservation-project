package spring_junyeong.__meetingRoom.repository;

import org.springframework.stereotype.Repository;
import spring_junyeong.__meetingRoom.domain.MeetingRoom;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class MeetingRoomRepository {
    List<MeetingRoom> MeetingRoomList = new CopyOnWriteArrayList<>();

}
