package com.meeting.reservation.domain.reservation;

import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Reservations {

    private final MeetingRoomId meetingRoomId;
    private final List<Reservation> values;

    public static Reservations create(MeetingRoomId meetingRoomId, List<Reservation> values) {
        return new Reservations(meetingRoomId, values);
    }

    private Reservations(MeetingRoomId meetingRoomId, List<Reservation> values) {
        this.meetingRoomId = meetingRoomId;
        this.values = values;
    }

    public void validateReserve(Reservation target) {
        for (Reservation reservation : values) {
            if (reservation.equals(target)) {
                throw new IllegalArgumentException("이미 존재하는 예약입니다.");
            }
            if (reservation.overlapTime(target)) {
                throw new IllegalArgumentException("이미 시간이 겹치는 예약이 존재합니다.");
            }
        }
    }

    public void validateCancel(Long id, String password, LocalDateTime now) {
        Reservation target = values.stream()
                                   .filter(reservation -> reservation.isEqualId(id))
                                   .findAny()
                                   .orElseThrow(ReservationNotFoundException::new);

        if (target.afterStartTime(now)) {
            throw new IllegalArgumentException("지금 회의실을 사용하고 있거나 이미 사용했습니다.");
        }
        if (!target.matchPassword(password)) {
            throw new InvalidReservationPasswordException();
        }
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(values);
    }

    public Long getMeetingRoomId() {
        return this.meetingRoomId.getValue();
    }

    public static class ReservationNotFoundException extends IllegalArgumentException {

        public ReservationNotFoundException() {
            super("지정한 ID에 해당하는 예약을 찾을 수 없습니다.");
        }
    }

    public static class InvalidReservationPasswordException extends IllegalArgumentException {

        public InvalidReservationPasswordException() {
            super("예약 비밀번호가 일치하지 않습니다.");
        }
    }
}
