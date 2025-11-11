package com.example.sesac_spring_practice_01.data;


import com.example.sesac_spring_practice_01.domain.reservation.Reservation;
import com.example.sesac_spring_practice_01.domain.reservation.repository.InMemoryReservationRepository;
import com.example.sesac_spring_practice_01.domain.room.Room;
import com.example.sesac_spring_practice_01.domain.room.repository.InMemoryRoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

@Component
public class DummyDataInitializer implements CommandLineRunner {

    private final InMemoryRoomRepository roomRepository;
    private final InMemoryReservationRepository reservationRepository;
    private final Random random = new Random();

    public DummyDataInitializer(InMemoryRoomRepository roomRepository,
                                InMemoryReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) {
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 10; i++) {
            Room room = Room.create(
                    "회의실 " + i,
                    LocalTime.of(9, 0),
                    LocalTime.of(20, 0)
            );
            roomRepository.save(room);
            for (int j = 0; j < 3; j++) {
                LocalDateTime start = LocalDateTime.now().withHour(9 + j * 3).withMinute(0);
                LocalDateTime end = start.plusHours(2);

                Reservation reservation = Reservation.create(
                        room.getId(),
                        start,
                        end,
                        "예약자" + i + "-" + (j + 1),
                        "010-" + (1000 + random.nextInt(9000)) + "-" + (1000 + random.nextInt(9000)),
                        String.format("%04d", random.nextInt(10000))
                );
                reservationRepository.save(reservation);
            }
        }
    }
}