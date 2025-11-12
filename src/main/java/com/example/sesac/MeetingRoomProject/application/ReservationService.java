package com.example.sesac.MeetingRoomProject.application;

import com.example.sesac.MeetingRoomProject.domain.MeetingRoom;
import com.example.sesac.MeetingRoomProject.domain.Reservation;
import com.example.sesac.MeetingRoomProject.exceptions.InvalidPasswordException;
import com.example.sesac.MeetingRoomProject.exceptions.ReservationConflictException;
import com.example.sesac.MeetingRoomProject.exceptions.ResourceNotFoundException;
import com.example.sesac.MeetingRoomProject.infrastructure.ListMeetingRoomRepository;
import com.example.sesac.MeetingRoomProject.infrastructure.ListReservationRepository;
import com.example.sesac.MeetingRoomProject.presentation.ReservationDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ListMeetingRoomRepository listMeetingRoomRepository;
    private final ListReservationRepository listReservationRepository;
    private final ModelMapper modelMapper;

    public ReservationService(ListMeetingRoomRepository listMeetingRoomRepository,
                              ListReservationRepository listReservationRepository,
                              ModelMapper modelMapper) {
        this.listMeetingRoomRepository = listMeetingRoomRepository;
        this.listReservationRepository = listReservationRepository;
        this.modelMapper = modelMapper;
    }

    public ReservationDto createReservation(Long roomId, ReservationDto reservationDto) {
        MeetingRoom meetingRoom = listMeetingRoomRepository.getMeetingRoomById(roomId);
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        reservation.setRoomId(meetingRoom.getId());

        listReservationRepository.findReservationsByRoomId(roomId).stream()
                .filter(existed -> existed.isOverlapped(reservation))
                .findAny()
                .ifPresent(existed -> {
                    throw new ReservationConflictException("이미 예약된 시간입니다.");
                });

        if(!reservation.getStartTime().isBefore(reservation.getEndTime())) {
            throw new IllegalArgumentException("종료 시간은 시작 시간보다 늦어야 합니다.");
        }

        Reservation savedReservation = listReservationRepository.addReservation(reservation);
        ReservationDto savedReservationDto = modelMapper.map(savedReservation, ReservationDto.class);

        return savedReservationDto;
    }

    public List<ReservationDto> getReservationsByRoomId(Long roomId) {

        listMeetingRoomRepository.getMeetingRoomById(roomId);

        List<Reservation> reservations =listReservationRepository.findReservationsByRoomId(roomId);
        List<ReservationDto> reservationDtos =reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .toList();
        return reservationDtos;
    }

    public void deleteReservation(Long roomId, Long reservationId, String password){
        Reservation reservation = listReservationRepository.findById(reservationId);

        if (!reservation.getRoomId().equals(roomId)) {
            //
            throw new ResourceNotFoundException("요청한 회의실의 예약이 아닙니다.");
        }

        if (!reservation.getPassword().equals(password)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
        listReservationRepository.deleteReservation(reservationId);
    }
}
