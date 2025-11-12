package com.example.ReserveSystem.InfraStructure;

import com.example.ReserveSystem.Domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ListReserveRepository {
    private List<Reservation> reservationList = new CopyOnWriteArrayList<>();
    private AtomicInteger index  = new AtomicInteger();

    // 새 예약 생성하기
    public Reservation createReservation(Reservation reservation){
        reservation.setId(index.getAndAdd(1));
        reservationList.add(reservation);
        return reservation;
    }
    // 특정 회의실의 모든 예약 현황을 조회하기
    public List<Reservation> findByRoomId(Integer roomId){
        return reservationList.stream()
                .filter(reservation -> reservation.sameRoomId(roomId))
                .toList();
    }
    // 특정 예약 조회하기
    public Reservation findById(Integer id){
        return reservationList.stream()
                .filter(reservation -> reservation.sameId(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("예약을 찾지 못했습니다."));
    }

    //특정 예약을 취소(삭제)하기
    public void delete(Integer id, String password){
        Reservation deletedReservation = this.findById(id);
        reservationList.remove(deletedReservation);
    }


}
