package spring_junyeong.__meetingRoom.domain.dto;

import java.time.LocalDateTime;

/**
 * 예약 조회/생성 성공 시 클라이언트에게 반환하는 응답 DTO
 * 비밀번호와 같은 민감한 정보는 제외합니다.
 */
public class ReservationResponseDto {
    private Long id;
    private Long meetingRoomId;
    private String roomName; // MeetingRoom에서 가져옴
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String userName;
    private String phoneNumber;
    private int participantCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(Long meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }
}