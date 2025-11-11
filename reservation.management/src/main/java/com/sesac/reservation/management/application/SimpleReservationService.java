package com.sesac.reservation.management.application;

import com.sesac.reservation.management.domain.Reservation;
import com.sesac.reservation.management.domain.Room;
import com.sesac.reservation.management.infrastructure.ListReservationRepository;
import com.sesac.reservation.management.infrastructure.ListRoomRepository;
import com.sesac.reservation.management.presentation.ReservationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;

@Service
public class SimpleReservationService {
    private ListReservationRepository listReservationRepository;
    private ListRoomRepository listRoomRepository;
    private ModelMapper modelMapper;

    @Autowired
    public SimpleReservationService(ListReservationRepository listReservationRepository,
                                    ListRoomRepository listRoomRepository,
                                    ModelMapper modelMapper) {
        this.listReservationRepository = listReservationRepository;
        this.listRoomRepository = listRoomRepository;
        this.modelMapper = modelMapper;
    }

    public ReservationDto add(ReservationDto reservationDto) {
        // 1) 예외 처리: 존재하지 않는 회의실 ID로 예약하려는 경우
        Room room = listRoomRepository.findById(reservationDto.getRoomId());

        // 2) 기본 유효성 검사
        validateReservation(reservationDto, room);

        // 3) 예약 충돌 감지
        validateTimeConflict(reservationDto);

        // 4) 예약 생성
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

    public void delete(Integer id, String password) {
        Reservation reservation = listReservationRepository.findById(id);

        // 비밀번호가 틀릴 경우, HTTP status로 응답해야 합니다.
        if (!reservation.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        listReservationRepository.delete(id);
    }

    // 기본 유효성 검사
    private void validateReservation(ReservationDto reservationDto, Room room) {
        if (reservationDto.getRoomId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회의실 ID는 필수입니다.");
        }
        if (reservationDto.getName() == null || reservationDto.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "예약자명은 필수입니다.");
        }
        if (reservationDto.getPhoneNumber() == null || reservationDto.getPhoneNumber().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "전화번호는 필수입니다.");
        }
        if (reservationDto.getPassword() == null || reservationDto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 필수입니다.");
        }
        if (reservationDto.getStart() == null || reservationDto.getEnd() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "시작/종료 시간은 필수입니다.");
        }
        if (!reservationDto.getEnd().isAfter(reservationDto.getStart())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "종료 시간은 시작 시간보다 늦어야 합니다.");
        }
        if (reservationDto.getAttendeeCount() == null || reservationDto.getAttendeeCount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "참석 인원은 1명 이상이어야 합니다.");
        }
        if (reservationDto.getAttendeeCount() > room.getMaxPool()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회의실 최대 수용 인원을 초과했습니다.");
        }
    }

    // 예약 충돌 감지
    private void validateTimeConflict(ReservationDto reservationDto) {
        List<Reservation> sameRoomReservations =
                listReservationRepository.findByRoomId(reservationDto.getRoomId());

        LocalTime newStart = reservationDto.getStart();
        LocalTime newEnd = reservationDto.getEnd();

        for (Reservation existing : sameRoomReservations) {
            LocalTime existingStart = existing.getStart();
            LocalTime existingEnd = existing.getEnd();

            // [start, end) 구간으로 보고 겹침 여부 판단
            boolean overlaps =
                    newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);

            if (overlaps) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 해당 시간에 예약이 존재합니다.");
            }
        }
    }
}
