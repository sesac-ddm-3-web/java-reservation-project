package com.jiwoo.MeetingRoom.application;

import com.jiwoo.MeetingRoom.application.exception.BadRequestException;
import com.jiwoo.MeetingRoom.application.exception.ConflictException;
import com.jiwoo.MeetingRoom.application.exception.ResourceNotFoundException;
import com.jiwoo.MeetingRoom.application.exception.UnauthorizedException;
import com.jiwoo.MeetingRoom.domain.Reservation;
import com.jiwoo.MeetingRoom.infrastructure.MeetingRoomRepository;
import com.jiwoo.MeetingRoom.infrastructure.ReserveationRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    ReserveationRpository reservationRepository;
    MeetingRoomRepository meetingRoomRepository;

    @Autowired
    public ReservationService(ReserveationRpository reservationRepository, MeetingRoomRepository meetingRoomRepository) {
        this.reservationRepository = reservationRepository;
        this.meetingRoomRepository = meetingRoomRepository;
    }

    public Reservation add(Reservation reservation) {
        if (meetingRoomRepository.findById(reservation.getRoomId()) == null) {
            throw new ResourceNotFoundException("회의실이 없습니다");
        }
        if (!reservation.getEndAt().isAfter(reservation.getStartAt())) {
            throw new BadRequestException("회의 종료 시간이 시작 시간 보다 빠릅니다.");
        }

        boolean timeCheck = reservationRepository.findByRoomId(reservation.getRoomId()).stream()
                .anyMatch(r ->
                        reservation.getStartAt().isBefore(r.getEndAt()) &&
                                reservation.getEndAt().isAfter(r.getStartAt())
                );
        if (timeCheck) {
            throw new ConflictException("기존 예약과 시간이 겹칩니다.");
        }

        return reservationRepository.add(reservation);
    }

    public List<Reservation> findByRoomId(Long roomId) {
        return reservationRepository.findByRoomId(roomId);
    }

    public boolean delete(Long id, String password) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new ResourceNotFoundException("해당 예약을 찾을 수 없습니다.");
        }
        if (!reservation.getGuest().getPassword().equals(password)) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
        return reservationRepository.delete(id);
    }
}