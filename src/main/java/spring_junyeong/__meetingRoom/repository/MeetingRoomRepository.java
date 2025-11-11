package spring_junyeong.__meetingRoom.repository;

import org.springframework.stereotype.Repository;
import spring_junyeong.__meetingRoom.domain.MeetingRoom;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class MeetingRoomRepository {
    List<MeetingRoom> meetingRooms = new CopyOnWriteArrayList<>();

    public MeetingRoomRepository() {
        this.initialize();
    }

    public Optional<MeetingRoom> findById(Long id){
        return meetingRooms.stream()
                .filter(room -> room.sameId(id))
                .findFirst();
    }

    public void initialize() {
        meetingRooms.clear();

        meetingRooms.add(new MeetingRoom("회의실 A - 대형", 8, 20));
        meetingRooms.add(new MeetingRoom("회의실 B - 중형", 6, 12));
        meetingRooms.add(new MeetingRoom("회의실 C - 소형", 3, 8));
        meetingRooms.add(new MeetingRoom("브레인스토밍 룸", 3, 20));
        meetingRooms.add(new MeetingRoom("집중학습룸 1호", 3, 12));
        meetingRooms.add(new MeetingRoom("집중학습룸 2호", 3, 12));
        meetingRooms.add(new MeetingRoom("면접실", 3, 6));
    }

    public List<MeetingRoom> getMeetingRoomList() {
        return meetingRooms;
    }
}
