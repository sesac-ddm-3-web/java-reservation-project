package com.example.reservation.infrastructure;

import com.example.reservation.domain.Room;
import com.example.reservation.domain.Reservation;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ListReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    @PostConstruct
    void init() {
        rooms.clear();
        for (int i = 1; i <= 4; i++) {
            rooms.add(new Room(i));
        }
    }

    public Reservation add(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(product -> product.sameId(id))
                .findFirst()
                .orElseThrow();
    }

    public List<Room> findAllRoom() {
        return rooms;
    }

    public List<Reservation> findReservationByRoom(Long roomId) {
        return reservations.stream()
                .filter(reservation -> reservation.sameRoomId(roomId)).toList();
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
