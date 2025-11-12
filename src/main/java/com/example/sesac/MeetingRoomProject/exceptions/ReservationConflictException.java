package com.example.sesac.MeetingRoomProject.exceptions;

public class ReservationConflictException extends RuntimeException{
    public ReservationConflictException(String message) {
        super(message);
    }
}
