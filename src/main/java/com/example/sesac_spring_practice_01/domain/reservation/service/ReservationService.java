package com.example.sesac_spring_practice_01.domain.reservation.service;

import com.example.sesac_spring_practice_01.domain.reservation.Reservation;
import com.example.sesac_spring_practice_01.domain.reservation.dto.request.ReservationCancelReqDto;
import com.example.sesac_spring_practice_01.domain.reservation.dto.request.ReservationCreateReqDto;
import com.example.sesac_spring_practice_01.domain.reservation.dto.response.ReservationCompleteResDto;
import com.example.sesac_spring_practice_01.domain.reservation.dto.response.ReservationStatusResDto;
import com.example.sesac_spring_practice_01.domain.reservation.exception.ReservationNotFoundException;
import com.example.sesac_spring_practice_01.domain.reservation.exception.ReservationTimeConflictException;
import com.example.sesac_spring_practice_01.domain.reservation.exception.ReservationTimeNotValidException;
import com.example.sesac_spring_practice_01.domain.reservation.repository.ReservationRepository;
import com.example.sesac_spring_practice_01.domain.room.Room;
import com.example.sesac_spring_practice_01.domain.room.exception.RoomNotFoundException;
import com.example.sesac_spring_practice_01.domain.room.repository.RoomRepository;
import com.example.sesac_spring_practice_01.global.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public List<ReservationStatusResDto> getAllReservations(Long roomId) {
        loadRoomOrThrow(roomId);
        List<Reservation> reservations = reservationRepository.findByRoomId(roomId);
        return ReservationStatusResDto.fromEntities(reservations);
    }

    public ReservationCompleteResDto createReservation(Long roomId, ReservationCreateReqDto request){
        Room room = loadRoomOrThrow(roomId);
        validateTime(request);
        room.validateReservationAllowed(request.getStartTime().toLocalTime(), request.getEndTime().toLocalTime());
        List<Reservation> bookedReservationsByRoom = reservationRepository.findByRoomId(roomId);
        isOverlappingWith(request, bookedReservationsByRoom);
        Reservation reservation = Reservation.create(
                roomId,
                TimeUtils.snapToMinute(request.getStartTime()),
                TimeUtils.snapToMinute(request.getEndTime()),
                request.getBookerName(),
                request.getBookerPhone(),
                request.getBookerPassword()
        );
        reservationRepository.save(reservation);
        return ReservationCompleteResDto.from(reservation);
    }

    public void deleteReservation(Long roomId, Long reservationId, ReservationCancelReqDto request){
        loadRoomOrThrow(roomId);
        Reservation reservation = loadReservationOrThrow(roomId, reservationId);
        reservation.checkPassword(request.getPassword());
        reservationRepository.delete(reservationId);
    }

    private Reservation loadReservationOrThrow(Long roomId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);
        if (!reservation.sameRoomId(roomId)) {
            throw new ReservationNotFoundException();
        }
        return reservation;
    }

    private Room loadRoomOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    private void validateTime(ReservationCreateReqDto request) {
        if (!request.getStartTime().isBefore(request.getEndTime())){
            throw new ReservationTimeNotValidException();
        }
    }

    private void isOverlappingWith(ReservationCreateReqDto request, List<Reservation> bookedReservationsByRoom) {
        boolean overlap = bookedReservationsByRoom.stream()
                .anyMatch(reservation -> reservation.overlaps(request.getStartTime(), request.getEndTime()));
        if (overlap){
            throw new ReservationTimeConflictException();
        }
    }
}
