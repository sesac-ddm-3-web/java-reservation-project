package com.example.ReserveSystem.Domain;


import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;


public class Reservation {
    private Integer id;
    private Integer roomId;
    private String startDate;
    private String endDate;

    // 비회원
    @Size(min = 1)
    private String userName;
    @Size(min = 1)
    private String phoneNumber;
    @Size(min = 1, max = 30)
    private String password;


    public void setId(Integer id){
        this.id = id;
    }


    public Boolean sameRoomId(Integer roomId){
        return this.roomId.equals(roomId);
    }

    public Boolean sameId(Integer id){
        return this.id.equals(id);
    }


    // 기존의 예약시간이랑 겹치는지  검사하는 로직
    public boolean isTimeOverlapping(Reservation reservation){
        LocalDateTime newStartDate = LocalDateTime.parse(reservation.startDate);
        LocalDateTime newEndDate = LocalDateTime.parse(reservation.endDate);
        LocalDateTime existingStartDate = LocalDateTime.parse(this.startDate);
        LocalDateTime existingEndDate = LocalDateTime.parse(this.endDate);
        // 새 시작 < 기존 끝 && 새 끝 < 기존 시작
        return newStartDate.isBefore(existingEndDate) && newEndDate.isAfter(existingStartDate);
    }

    public boolean checkPassword(String password){
        return password.equals(this.password);
    }

    @Override
    public boolean equals(Object o){
        if(this == o ) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return Objects.equals(id, reservation.id);
    }


}
