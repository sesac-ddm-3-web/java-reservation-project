package spring_junyeong.__meetingRoom.repository;

import org.springframework.stereotype.Repository;
import spring_junyeong.__meetingRoom.domain.Reservation;
import spring_junyeong.__meetingRoom.domain.error.ReservationNotFoundError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {
    private static final AtomicLong sequence = new AtomicLong(0L);
    List<Reservation> reservations = new ArrayList<>();

    public Reservation add(Reservation reservation) {
        reservation.setId(sequence.incrementAndGet());
        reservations.add(reservation);
        return reservation;
    }

    public void remove(Long reservationId) {
        Reservation removeReservation = getReservationById(reservationId);
        reservations.remove(removeReservation);
    }

    public List<Reservation> getReservations(){
        return reservations;
    }

    public Reservation getReservationById(Long reservationId) {
        return reservations.stream().filter(reservation -> reservation.getId().equals(reservationId)).findFirst().orElseThrow(()-> new ReservationNotFoundError("해당 ID에 예약 정보를 찾지 못했습니다."));
    }

    public List<Reservation> getReservationsByRoomId(Long roomId) {
        return reservations.stream().filter(reservation -> reservation.isSameRoomReserved(roomId)).toList();
    }


}
