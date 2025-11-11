package org.example.javareservationproject.infra;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.example.javareservationproject.domain.reservation.Reservation;
import org.example.javareservationproject.domain.reservation.Reservations;
import org.example.javareservationproject.domain.reservation.repository.ReservationRepository;
import org.example.javareservationproject.infra.storage.SnapshotBinding;
import org.example.javareservationproject.infra.storage.support.SnapshotRepositorySupport;
import org.example.javareservationproject.infra.storage.SnapshotStorage;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InMemoryReservationRepository implements ReservationRepository, SnapshotRepositorySupport<Reservation> {

    private static final List<Reservation> DB = new CopyOnWriteArrayList<>();
    private static final AtomicLong SEQUENCE = new AtomicLong(1);

    private final String storageKey = "reservations";
    private final SnapshotStorage<Reservation> storage;

    @Override
    public Reservations findByMeetingRoomId(Long meetingRoomId) {
        TreeSet<Reservation> reservations = DB.stream()
            .filter(r -> meetingRoomId.equals(r.getMeetingRoomId()))
            .collect(Collectors.toCollection(TreeSet::new));

        return new Reservations(reservations);
    }

    @Override
    public Optional<Reservation> findByIdAndMeetingRoomId(Long meetingRoomId, Long id) {
        return DB.stream()
            .filter(r -> (meetingRoomId.equals(r.getMeetingRoomId()) && id.equals(r.getId())))
            .findFirst();
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservation.setId(SEQUENCE.getAndIncrement());
        DB.add(reservation);

        return reservation;
    }

    @Override
    public void delete(Reservation reservation) {
        DB.remove(reservation);
    }

    /**
     * 데이터 로딩, 저장
     */
    @PostConstruct
    private void init() {
        loadSnapshot();
    }

    @PreDestroy
    private void shutdown() {
        writeSnapshot();
    }

    @Override
    public SnapshotBinding<Reservation> binding() {
        return new SnapshotBinding<>(Reservation.class, SEQUENCE, DB, storageKey, storage);
    }
}
