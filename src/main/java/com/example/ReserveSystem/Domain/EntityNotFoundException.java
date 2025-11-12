package com.example.ReserveSystem.Domain;

public class EntityNotFoundException extends   RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
