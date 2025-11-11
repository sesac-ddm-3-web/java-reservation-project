package spring_junyeong.__meetingRoom.domain;

import java.util.concurrent.atomic.AtomicLong;

public class MeetingRoom {
    Long id;
    String name;
    Integer maxReservationTime; // 최대 이용 가능 시간
    Integer maxCapacity; // 최대 수용 인원
    private static final AtomicLong sequence = new AtomicLong(0L);

    public MeetingRoom(String name, Integer maxReservationTime, Integer maxCapacity){
        this.id = sequence.incrementAndGet();
        this.name = name;
        this.maxReservationTime = maxReservationTime;
        this.maxCapacity = maxCapacity;
    }

    public Long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean sameId(Long id){
        return this.id.equals(id);
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

}
