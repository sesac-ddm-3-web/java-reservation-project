package com.example.ReserveSystem.Domain;


public class ReservationConflictException extends RuntimeException{
    public ReservationConflictException(String message){
        super(message);
    }
}
