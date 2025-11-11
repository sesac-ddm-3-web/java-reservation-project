package spring_junyeong.__meetingRoom.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private Long id;
    private MeetingRoom room;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer participantCount;
    private User reservationUser;

    public Reservation() {
        // ModelMapper가 객체를 생성할 때 사용
    }

    public Reservation(MeetingRoom room, LocalDateTime startTime, LocalDateTime endTime, Integer participantCount, User reservationUser) {
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participantCount = participantCount;
        this.reservationUser = reservationUser;
    }

    public Long getId(){
        return id;
    }

    public MeetingRoom getRoom() {
        return room;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() { return endTime; }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public User getReservationUser(){
        return this.reservationUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoom(MeetingRoom room) {
        this.room = room;
    };

    public void setUser(User user){
        this.reservationUser = user;
    };

    public boolean isSameRoomReserved(Long roomId){
        return Objects.equals(room.getId(), roomId);
    }

    public boolean isReserveOverwrap(Reservation reservation){
        // 핵심 로직 : earlyEnd < latelyStart 이기만 하다면 겹치지 않는다.
        Reservation earlyStartReservation;
        Reservation latelyStartReservation;

        // 1. 두개의 예약 정렬
        if(reservation.getStartTime().isBefore(this.startTime)) {
            earlyStartReservation = reservation;
            latelyStartReservation = this;
        }
        else if(this.startTime.isBefore(reservation.getStartTime())) {
            earlyStartReservation = this;
            latelyStartReservation = reservation;
        }
        else return true; // 2. 두 예약의 시작 시간이 같으면 false

        // 3. 이르게 시작한 타임의 끝나는 시간이 늦게 시작한 시간의 시작 시간보다 앞서지만 않으면 됨 = 겹치지 않는다!
        return !earlyStartReservation.getEndTime().isBefore(latelyStartReservation.getStartTime());
    }

}
