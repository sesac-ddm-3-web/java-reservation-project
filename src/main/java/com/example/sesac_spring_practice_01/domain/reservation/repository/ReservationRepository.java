package com.example.sesac_spring_practice_01.domain.reservation.repository;

import com.example.sesac_spring_practice_01.domain.reservation.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    List<Reservation> findByRoomId(Long roomId);
    void save(Reservation reservation);
    Optional<Reservation> findById(Long reservationId);
    void delete(Long reservationId);
}