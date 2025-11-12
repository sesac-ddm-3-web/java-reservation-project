package com.example.assignmant.domain;

public class Room {
    private Long id;
    private int roomNumber;
    private int maxCapacity;

    public Room(Long id, int roomNumber, int maxCapacity) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.maxCapacity = maxCapacity;
    }

    public Long getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }


    public boolean canAccommodate(int requestedCapacity) {
        return this.maxCapacity >= requestedCapacity;
    }
}
