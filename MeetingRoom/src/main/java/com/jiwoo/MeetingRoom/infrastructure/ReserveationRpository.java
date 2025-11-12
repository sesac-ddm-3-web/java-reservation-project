package com.jiwoo.MeetingRoom.infrastructure;

import com.jiwoo.MeetingRoom.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReserveationRpository {

    List<Reservation> reservations = new CopyOnWriteArrayList<>();
    AtomicLong sequence = new AtomicLong(1L);

    public Reservation add(Reservation reservation) {
        reservation.setId(sequence.getAndIncrement());
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> findByRoomId(Long roomId) {
        return reservations.stream()
                .filter(reservation -> reservation.getRoomId().equals(roomId))
                .toList();
    }

    public Reservation findById(Long id) {
        return reservations.stream().filter(reservation -> reservation.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean delete(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
