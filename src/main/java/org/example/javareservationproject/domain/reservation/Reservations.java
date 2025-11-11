package org.example.javareservationproject.domain.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.example.javareservationproject.domain.reservation.exception.ReservationTimeConflictException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Reservations {
    private TreeSet<Reservation> reservations;

    public boolean isTimeConflict(ReservationTime time) {
        return reservations.stream()
            .anyMatch(r ->
                r.getTime().isConflict(time.getDate(), time.getStartTime(), time.getEndTime())
            );
    }

    public void addReservation(Reservation reservation) {
        validateTimeConflict(reservation.getTime());
        reservations.add(reservation);
    }

    private void validateTimeConflict(ReservationTime time) {
        if (isTimeConflict(time)) {
            throw new ReservationTimeConflictException();
        }
    }

    public List<Reservation> getCopy() {
        return new ArrayList<>(reservations);
    }
}
