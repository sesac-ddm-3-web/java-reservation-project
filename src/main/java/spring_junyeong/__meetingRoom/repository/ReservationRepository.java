package spring_junyeong.__meetingRoom.repository;

import org.springframework.stereotype.Repository;
import spring_junyeong.__meetingRoom.domain.Reservation;

import java.util.HashMap;

@Repository
public class ReservationRepository {
    HashMap<String, Reservation> reservationStore;
}
