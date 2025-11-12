package com.example.reservation.application;

import com.example.reservation.domain.Room;
import com.example.reservation.domain.Reservation;
import com.example.reservation.exception.InvalidPasswordException;
import com.example.reservation.exception.ReservationNotFoundException;
import com.example.reservation.infrastructure.ListReservationRepository;
import com.example.reservation.presentation.ReservationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private ModelMapper modelMapper;
    private ListReservationRepository listReservationRepository;

    @Autowired
    ReservationService(ListReservationRepository listReservationRepository, ModelMapper modelMapper) {
        this.listReservationRepository = listReservationRepository;
        this.modelMapper = modelMapper;
    }

    public ReservationDto add(ReservationDto reservationDto) {
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        // checkValid(reservation);

        Reservation savedReservation = listReservationRepository.add(reservation);
        ReservationDto savedReservationDto = modelMapper.map(savedReservation, ReservationDto.class);

        return savedReservationDto;
    }

    public List<ReservationDto> findReservationByRoom(Long roomId) {
        List<Reservation> reservations = listReservationRepository.findReservationByRoom(roomId);
        List<ReservationDto> reservationDtos = reservations
                .stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .toList();
        return reservationDtos;
    }

    public List<Room> findAllRoom() {
        return listReservationRepository.findAllRoom();
    }

    public void delete(Long id, String password) {
        Reservation reservation = listReservationRepository.findById(id);
        if (reservation == null) throw new ReservationNotFoundException(id);

        if (!password.equals(reservation.getPassword())) {
            throw new InvalidPasswordException();
        }

        listReservationRepository.delete(reservation);
    }
}
