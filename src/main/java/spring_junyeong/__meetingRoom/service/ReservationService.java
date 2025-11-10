package spring_junyeong.__meetingRoom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_junyeong.__meetingRoom.repository.ReservationRepository;

@Service
public class ReservationService {
    ReservationRepository reservationRepository;

    @Autowired
    ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

}
