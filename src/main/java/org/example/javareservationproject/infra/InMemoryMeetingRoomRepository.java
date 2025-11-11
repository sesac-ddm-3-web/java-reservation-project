package org.example.javareservationproject.infra;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.example.javareservationproject.domain.meetingroom.MeetingRoom;
import org.example.javareservationproject.domain.meetingroom.repository.MeetingRoomRepository;
import org.example.javareservationproject.infra.storage.SnapshotBinding;
import org.example.javareservationproject.infra.storage.support.SnapshotRepositorySupport;
import org.example.javareservationproject.infra.storage.SnapshotStorage;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InMemoryMeetingRoomRepository implements MeetingRoomRepository, SnapshotRepositorySupport<MeetingRoom> {

    private static final List<MeetingRoom> DB = new CopyOnWriteArrayList<>();
    private static final AtomicLong SEQUENCE = new AtomicLong();

    private final String storageKey = "meeting_rooms";
    private final SnapshotStorage<MeetingRoom> storage;

    @Override
    public List<MeetingRoom> findAll() {
        return DB;
    }

    @Override
    public Optional<MeetingRoom> findById(Long id) {
        return DB.stream()
            .filter(room -> room.getId().equals(id))
            .findFirst();
    }

    /**
     * 데이터 로딩
     */
    @PostConstruct
    private void init() {
        loadSnapshot();
    }

    @Override
    public SnapshotBinding<MeetingRoom> binding() {
        return new SnapshotBinding<>(MeetingRoom.class, SEQUENCE, DB, storageKey, storage);
    }
}
