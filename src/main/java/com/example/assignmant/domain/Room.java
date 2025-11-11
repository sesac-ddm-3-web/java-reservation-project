package com.example.assignmant.domain;

public class Room {
    private Long id;
    private int roomNumber;
    private int maxCapacity;

    public Long getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }


}
