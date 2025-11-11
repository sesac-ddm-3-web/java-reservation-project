package org.example.javareservationproject.service;

import java.util.List;

import org.example.javareservationproject.domain.meetingroom.MeetingRoom;
import org.example.javareservationproject.domain.meetingroom.exception.MeetingRoomNotFoundException;
import org.example.javareservationproject.domain.meetingroom.repository.MeetingRoomRepository;
import org.example.javareservationproject.domain.reservation.Reservations;
import org.example.javareservationproject.domain.reservation.repository.ReservationRepository;
import org.example.javareservationproject.presentation.dto.MeetingRoomDto;
import org.example.javareservationproject.presentation.dto.MeetingRoomReservationsDto;
import org.example.javareservationproject.presentation.dto.MeetingRoomsDto;
import org.example.javareservationproject.presentation.dto.ReservationDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 회의실 전체 조회
     */
    public MeetingRoomsDto getMeetingRoomsInfo(Integer capacity) {
        List<MeetingRoom> meetingRooms;
        if (capacity != null) {
            meetingRooms = meetingRoomRepository.findByCapacity(capacity);
        } else {
            meetingRooms = meetingRoomRepository.findAll();
        }

        return new MeetingRoomsDto(
            meetingRooms.stream()
                .map(MeetingRoomDto::toDto)
                .toList()
        );
    }

    /**
     * 특정 회의실의 예약 목록 조회
     */
    public MeetingRoomReservationsDto getMeetingRoomReservations(long id) {
        MeetingRoom room = getByIdWithReservations(id);

        return new MeetingRoomReservationsDto(
            MeetingRoomDto.toDto(room),
            room.getReservations().getCopy()
                .stream()
                .map(ReservationDto::toDto)
                .toList()
        );
    }

    public MeetingRoom getById(long id) {
        return meetingRoomRepository.findById(id)
            .orElseThrow(MeetingRoomNotFoundException::new);
    }

    public MeetingRoom getByIdWithReservations(long id) {
        MeetingRoom room = meetingRoomRepository.findById(id)
            .orElseThrow(MeetingRoomNotFoundException::new);
        Reservations reservations = reservationRepository.findByMeetingRoomId(id);

        return MeetingRoom.consist(room, reservations);
    }
}
