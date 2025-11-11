package com.meeting.reservation.persistence;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.Reservations;
import com.meeting.reservation.domain.reservation.repository.ReservationRepository;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final AtomicLong idGenerator = new AtomicLong(1L);
    private final Map<MeetingRoomId, List<Reservation>> reservations = new ConcurrentHashMap<>();

    @Override
    public Reservation save(Reservation target) {
        Long reservationId = idGenerator.getAndAdd(1L);
        Reservation savedReservation = target.withAssignedId(reservationId);

        reservations.computeIfAbsent(target.getMeetingRoomId(), key -> new CopyOnWriteArrayList<>())
                    .add(savedReservation);

        return savedReservation;
    }

    @Override
    public List<Reservation> saveAll(List<Reservation> target) {
        List<Reservation> reservationList = new ArrayList<>();

        for (Reservation reservation : target) {
            reservationList.add(this.save(reservation));
        }

        return reservationList;
    }

    @Override
    public void delete(MeetingRoomId meetingRoomId, Long id) {
        List<Reservation> reservationList = reservations.get(meetingRoomId);

        if (reservationList == null) {
            throw new MeetingRoomReservationsNotFoundException();
        }

        Reservation target = reservationList.stream()
                                            .filter(reservation -> reservation.isEqualId(id))
                                            .findAny()
                                            .orElseThrow(NoReservationsForMeetingRoomException::new);

        reservationList.remove(target);
    }

    @Override
    public Reservation find(MeetingRoomId meetingRoomId, Long id) {
        List<Reservation> reservationList = reservations.get(meetingRoomId);

        if (reservationList == null) {
            throw new MeetingRoomReservationsNotFoundException();
        }

        return reservationList.stream()
                              .filter(reservation -> reservation.isEqualId(id))
                              .findAny()
                              .orElseThrow(NoReservationsForMeetingRoomException::new);
    }

    @Override
    public Reservations findAll(MeetingRoomId meetingRoomId) {
        List<Reservation> reservationList = reservations.get(meetingRoomId);

        return Reservations.create(
                meetingRoomId,
                Objects.requireNonNullElse(reservationList, Collections.emptyList())
        );
    }

    public static class MeetingRoomReservationsNotFoundException extends IllegalArgumentException {

        public MeetingRoomReservationsNotFoundException() {
            super("지정한 회의실 ID에 대한 예약을 찾지 못했습니다.");
        }
    }

    public static class NoReservationsForMeetingRoomException extends IllegalArgumentException {

        public NoReservationsForMeetingRoomException() {
            super("지정한 ID에 대한 예약을 찾지 못했습니다.");
        }
    }
}
