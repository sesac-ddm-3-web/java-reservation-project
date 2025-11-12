package com.example.assignmant.dto;

import jakarta.validation.constraints.NotNull;

public class ReservationDeleteDto {
    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String password;

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }


}
