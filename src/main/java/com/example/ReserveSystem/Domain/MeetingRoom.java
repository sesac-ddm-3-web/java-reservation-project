package com.example.ReserveSystem.Domain;

import java.lang.reflect.Method;

public class MeetingRoom {
    private Integer roomNumber;
    private Boolean isReserved;

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public Boolean getReserved() {
        return isReserved;
    }


    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }

    public MeetingRoom(Integer roomNumber, Boolean isReserved){
        this.roomNumber = roomNumber;
        this.isReserved = isReserved;
    }
}
