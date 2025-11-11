package org.example.javareservationproject.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.LongStream;

import org.example.javareservationproject.domain.reservation.exception.InvalidReservationException;
import org.example.javareservationproject.domain.reservation.exception.ReservationTimeConflictException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomReservations {
    private TreeSet<Reservation> reservations;

    public boolean isRepeatTimeConflict(ReservationTime target) {
        RepetitionType type = target.getType();
        if (type == null) {
            throw new InvalidReservationException("유효하지 않은 예약 반복 타입입니다.");
        }
        type.validate(target.getRepeatCnt());

        return reservations.stream()
            .map(Reservation::getTime)
            .anyMatch(existing -> existing.isConflict(target));
    }

    public void addReservation(Reservation reservation) {
        validateTimeConflict(reservation.getTime());
        reservations.add(reservation);
    }

    private void validateTimeConflict(ReservationTime time) {
        if (isRepeatTimeConflict(time)) {
            throw new ReservationTimeConflictException();
        }
    }

    public List<Reservation> getCopy() {
        return new ArrayList<>(reservations);
    }
}
