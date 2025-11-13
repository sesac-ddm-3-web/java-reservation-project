package com.example.sesac_spring_practice_01.domain.reservation.repository;

import com.example.sesac_spring_practice_01.domain.reservation.Reservation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations = new HashMap<>();

    public List<Reservation> findByRoomId(Long roomId) {
        return reservations.values()
                .stream()
                .filter(reservation -> reservation.sameRoomId(roomId))
                .toList();
    }

    public Optional<Reservation> findById(Long reservationId) {
        return Optional.ofNullable(reservations.get(reservationId));
    }

    public void save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public void delete(Long reservationId) {
        reservations.remove(reservationId);
    }
}
