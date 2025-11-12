package com.example.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(Long id) {
        super(id + ": 존재하지 않는 id입니다. ");
    }
}
