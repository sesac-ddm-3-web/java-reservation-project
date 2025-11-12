package com.example.assignmant.repository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository<T> {
    T create(T target);
    List<T> findAll();
    Optional<T> findById(Long id);
    void delete(Long id);
    List<T> findByRoomId(Long id);
}
