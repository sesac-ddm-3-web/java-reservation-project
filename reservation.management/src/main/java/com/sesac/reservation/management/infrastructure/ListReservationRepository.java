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
}
