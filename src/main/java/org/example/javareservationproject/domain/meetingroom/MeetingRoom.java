package org.example.javareservationproject.domain.meetingroom;

import org.example.javareservationproject.domain.reservation.Reservation;
import org.example.javareservationproject.domain.reservation.RoomReservations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class MeetingRoom {
    private Long id;
    private String name;
    private int capacity;
    private RoomReservations roomReservations;

    public static MeetingRoom consist(MeetingRoom meetingRoom, RoomReservations roomReservations) {
        meetingRoom.setRoomReservations(roomReservations);
        return meetingRoom;
    }

    public void makeReservation(Reservation reservation) {
        roomReservations.addReservation(reservation);
    }

    public boolean isOverCapacity(int headcount) {
        return this.capacity < headcount;
    }

    private void setRoomReservations(RoomReservations roomReservations) {
        this.roomReservations = roomReservations;
    }
}
