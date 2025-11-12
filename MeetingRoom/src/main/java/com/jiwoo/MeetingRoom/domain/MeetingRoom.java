package com.jiwoo.MeetingRoom.domain;

public class MeetingRoom {
    private Long id;
    private String roomName;

    public MeetingRoom(Long id,String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public Long getId() {
        return id;
    }


}

