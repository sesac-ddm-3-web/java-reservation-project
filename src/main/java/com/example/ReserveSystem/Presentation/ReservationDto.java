package com.example.ReserveSystem.Presentation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationDto {

    private Integer id;
    @NotNull
    private Integer roomId;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;

    @NotNull
    private String userName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;


    public String getEndDate() {
        return endDate;
    }


    public String getStartDate() {
        return startDate;
    }


    public String getUserName() {
        return userName;
    }

    public Integer getId(){
        return id;
    }

    public Integer getRoomId() {
        return roomId;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}

