package com.sesac.reservation.management.application;

import com.sesac.reservation.management.domain.Reservation;
import com.sesac.reservation.management.infrastructure.ListReservationRepository;
import com.sesac.reservation.management.presentation.ReservationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleReservationService {
    private ListReservationRepository listReservationRepository;
    private ModelMapper modelMapper;

    @Autowired
    public SimpleReservationService(ListReservationRepository listReservationRepository, ModelMapper modelMapper) {
        this.listReservationRepository = listReservationRepository;
        this.modelMapper = modelMapper;
    }

    public ReservationDto add(ReservationDto reservationDto) {
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);

        Reservation savedReservation = listReservationRepository.add(reservation);

        ReservationDto savedReservationDto = modelMapper.map(savedReservation, ReservationDto.class);

        return savedReservationDto;
    }

    public List<ReservationDto> findByRoomId(Integer roomId) {
        List<Reservation> reservations = listReservationRepository.findByRoomId(roomId);

        List<ReservationDto> reservationDtos = reservations.stream()
                .map(r -> modelMapper.map(r, ReservationDto.class))
                .toList();

        return reservationDtos;
    }

    public List<ReservationDto> findAll() {
        List<Reservation> reservations = listReservationRepository.findAll();

        List<ReservationDto> reservationDtos = reservations.stream()
                .map(r -> modelMapper.map(r, ReservationDto.class))
                .toList();

        return reservationDtos;
    }
}
