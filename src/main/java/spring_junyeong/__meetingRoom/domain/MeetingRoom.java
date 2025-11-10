package spring_junyeong.__meetingRoom.domain;

import java.util.List;

public class MeetingRoom {
    String id;
    Integer max_unsage_time; // 최대 이용 가능 시간
    Integer max_capacity; // 최대 수용 인원
    List<Participant> participants; // 참석자들
}
