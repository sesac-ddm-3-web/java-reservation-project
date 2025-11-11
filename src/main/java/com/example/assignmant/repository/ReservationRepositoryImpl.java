package com.example.assignmant.repository;

import com.example.assignmant.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository<Reservation> {
    Map<Long, Reservation> store = new ConcurrentHashMap<>();
    AtomicLong sequence = new AtomicLong(1L);


    @Override
    public Reservation create(Reservation reservation) {
        reservation.setId(sequence.getAndIncrement());

        store.put(reservation.getId(), reservation);

        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public List<Reservation> findByRoomId(Long roomId) {
        return store.values()
                .stream()
                .filter(reservation -> reservation.isSameRoom(roomId))
                .toList();
    }
}
