package org.example.javareservationproject.service;

import java.time.LocalDateTime;

import org.example.javareservationproject.common.exception.UnauthorizedException;
import org.example.javareservationproject.domain.meetingroom.MeetingRoom;
import org.example.javareservationproject.domain.reservation.Reservation;
import org.example.javareservationproject.domain.reservation.ReservationInfo;
import org.example.javareservationproject.domain.reservation.ReservationTime;
import org.example.javareservationproject.domain.reservation.exception.InvalidReservationException;
import org.example.javareservationproject.domain.reservation.exception.ReservationNotFoundException;
import org.example.javareservationproject.domain.reservation.repository.ReservationRepository;
import org.example.javareservationproject.presentation.dto.ReservationReqDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final MeetingRoomService meetingRoomService;
    private final ReservationRepository reservationRepository;

    /**
     * 예약하기
     */
    public synchronized void makeReservation(long roomId, ReservationReqDto request) {
        MeetingRoom room = meetingRoomService.getByIdWithReservations(roomId);
        validateRoomCapacity(request, room);

        Reservation reservation = Reservation.create(
            roomId,
            new ReservationTime(request.date(), request.startTime(), request.endTime(), request.repetitionType(), request.repeatCnt()),
            new ReservationInfo(request.headcount(), request.client(), request.phoneNumber(), request.password()),
            LocalDateTime.now()
        );

        room.makeReservation(reservation);
        reservationRepository.save(reservation);
    }

    private void validateRoomCapacity(ReservationReqDto request, MeetingRoom room) {
        if (room.isOverCapacity(request.headcount())) {
            throw new InvalidReservationException(
                "수용 가능한 인원을 초과했습니다. 수용 가능 인원: " + room.getCapacity()
                    + ", 입력 인원: " + request.headcount()
            );
        }
    }

    /**
     * 예약 삭제하기
     */
    public synchronized void delete(long roomId, long reservationId, String password) {
        Reservation reservation = reservationRepository.findByIdAndMeetingRoomId(roomId, reservationId)
            .orElseThrow(ReservationNotFoundException::new);
        ReservationInfo info = reservation.getInfo();

        if (!info.isPasswordMatches(password)) {
            throw new UnauthorizedException("예약 시 입력한 비밀번호가 일치하지 않습니다.");
        }
        reservationRepository.delete(reservation);
    }
}
