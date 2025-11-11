package com.sesac.reservation.management.infrastructure;

import com.sesac.reservation.management.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ListReservationRepository {
    private List<Reservation> reservations = new CopyOnWriteArrayList<>();
    private AtomicInteger sequence = new AtomicInteger(1);

    public Reservation add(Reservation reservation) {
        reservation.setId(sequence.getAndAdd(1));

        reservations.add(reservation);

        return reservation;
    }

    public List<Reservation> findByRoomId(Integer roomId) {
        return reservations.stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .toList();
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public Reservation findById(Integer id) {
        return reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    public void delete(Integer id) {
        Reservation reservation = this.findById(id);

        reservations.remove(reservation);
    }
}
