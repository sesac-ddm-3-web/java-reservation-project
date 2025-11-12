package com.example.sesac.MeetingRoomProject.infrastructure;

import com.example.sesac.MeetingRoomProject.domain.Reservation;
import com.example.sesac.MeetingRoomProject.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListReservationRepository {
    List<Reservation> reservations = new CopyOnWriteArrayList<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    public Reservation addReservation(Reservation reservation) {
        reservation.setId(sequence.getAndAdd(1L));
        reservations.add(reservation);
        return reservation;
    }

    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(r ->r.sameId(id))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Reservation을 찾지 못했습니다."));
    }

    public List<Reservation> findReservationsByRoomId(Long roomId) {
        return reservations.stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .toList();
    }

    public void deleteReservation(Long id){
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }
}
