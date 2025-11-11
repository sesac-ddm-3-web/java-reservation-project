package com.example.assignmant.service;

import com.example.assignmant.domain.Reservation;
import com.example.assignmant.dto.ReservationDeleteDto;
import com.example.assignmant.dto.ReservationDto;
import com.example.assignmant.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository<Reservation> reservationRepository;
    private final ModelMapper modelMapper;
    private final ReservationValidator reservationValidator;

    public ReservationService(
            ReservationRepository<Reservation> reservationRepository,
            ModelMapper modelMapper,
            ReservationValidator reservationValidator
    ) {
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
        this.reservationValidator = reservationValidator;
    }

    public ReservationDto createReservation(Long roomId, ReservationDto reservationDto) {
        reservationValidator.validate(reservationDto);

        checkReservationConflict(roomId, reservationDto);

        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);

        Reservation created = reservationRepository.create(reservation);

        return modelMapper.map(created, ReservationDto.class);
    }


    public List<ReservationDto> findAllReservations(Long roomId) {
        List<Reservation> reservations = reservationRepository.findByRoomId(roomId);
        return reservations
                .stream()
                .map(reservation -> modelMapper.map(reservation,ReservationDto.class))
                .sorted()
                .toList();
    }

    public ReservationDto findByReservationIdInRoom(Long roomId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));

        // 해당 방의 예약이 맞는지 검증
        if (!reservation.isSameRoom(roomId)) {
            throw new IllegalArgumentException("해당 방의 예약이 아닙니다.");
        }

        return modelMapper.map(reservation, ReservationDto.class);
    }

    public void deleteReservation(
            Long roomId,
            Long reservationId,
            ReservationDeleteDto deleteDto
    ) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));


        if (!reservation.isSameRoom(roomId)) {
            throw new IllegalArgumentException("해당 방의 예약이 아닙니다.");
        }

        if(reservation.isOwner(
                deleteDto.getName(),
                deleteDto.getPhoneNumber(),
                deleteDto.getPassword()
        )) {
            throw new IllegalArgumentException("예약 정보 일치하지 않습니다.");
        }

        reservationRepository.delete(reservationId);
    }


    private void checkReservationConflict(
            Long roomId,
            ReservationDto reservationDto
    ) {
        List<Reservation> reservations = reservationRepository.findByRoomId(roomId);

        LocalDateTime startTime = reservationDto.getStartTime();
        LocalDateTime endTime = reservationDto.getEndTime();

        reservations.stream()
                .filter(reservation -> reservation.isOverlapping(startTime, endTime))
                .findAny()
                .ifPresent(reservation -> {
                    throw new IllegalArgumentException("해당 시간대에 이미 예약이 존재합니다.");
                });
    }


}
