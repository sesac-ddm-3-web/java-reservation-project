package org.example.javareservationproject.domain.meetingroom;

import org.example.javareservationproject.domain.reservation.Reservation;
import org.example.javareservationproject.domain.reservation.Reservations;

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
    private Reservations reservations;

    public static MeetingRoom consist(MeetingRoom meetingRoom, Reservations reservations) {
        meetingRoom.setReservations(reservations);
        return meetingRoom;
    }

    public void makeReservation(Reservation reservation) {
        reservations.addReservation(reservation);
    }

    public boolean isOverCapacity(int headcount) {
        return this.capacity < headcount;
    }

    private void setReservations(Reservations reservations) {
        this.reservations = reservations;
    }
}
