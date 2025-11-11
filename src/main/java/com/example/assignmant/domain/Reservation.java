package com.example.assignmant.domain;

import com.example.assignmant.Constant;

import java.time.LocalDateTime;

public class Reservation implements Comparable<Reservation> {
    private Long id;
    private String name;
    private String phoneNumber;
    private String password;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long roomId;

    public Reservation() {
    }

    public Reservation(String name,
                       String phoneNumber,
                       String password,
                       LocalDateTime startTime,
                       LocalDateTime endTime,
                       Long roomId
    ) {
        validateName(name);
        validatePassword(password);
        validateSameDay(startTime, endTime);
        validateRoomId(roomId);

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomId = roomId;
    }

    public boolean isSameRoom(Long id) {
        return this.roomId.equals(id);
    }

    public boolean isOverlapping(
            LocalDateTime otherStart,
            LocalDateTime otherEnd
    ) {
        return this.startTime.isBefore(otherEnd) &&
                otherStart.isBefore(this.endTime);
    }

    public boolean isOwner(String name, String phoneNumber, String password) {
        return this.name.equals(name)
                && this.phoneNumber.equals(phoneNumber)
                && this.password.equals(password);
    }

    @Override
    public int compareTo(Reservation other) {
        // startTime 기준으로 먼저 비교
        int startTimeComparison = this.startTime.compareTo(other.startTime);

        // startTime이 같으면 endTime으로 비교
        if (startTimeComparison == 0) {
            return this.endTime.compareTo(other.endTime);
        }

        return startTimeComparison;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private void validateName(String name) {
        if (name.length() > 20) {
            throw new IllegalArgumentException("이름은 20자 이내로 해주세요");
        }
    }

    private void validateRoomId(Long id) {
        if (id < Constant.MIN_ROOM_NUMBER || id > Constant.MAX_ROOM_NUMBER) {
            throw new IllegalArgumentException("방 갯수를 확인해주세요");
        }
    }

    private void validatePassword(String password) {
        if (password.length() > 4) {
            throw new IllegalArgumentException("비밀번호는 4자리 이상이어야 합니다.");
        }
    }

    private void validateSameDay(LocalDateTime startTime, LocalDateTime endTime) {
        if (!startTime.toLocalDate().equals(endTime.toLocalDate())) {
            throw new IllegalArgumentException("예약은 하루를 넘을 수 없습니다.");
        }
    }


}
