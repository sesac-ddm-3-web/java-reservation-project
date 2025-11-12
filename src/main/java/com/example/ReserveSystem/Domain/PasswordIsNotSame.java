package com.example.ReserveSystem.Domain;

public class PasswordIsNotSame extends RuntimeException {
    public PasswordIsNotSame(String message){
        super(message);
    }
}
