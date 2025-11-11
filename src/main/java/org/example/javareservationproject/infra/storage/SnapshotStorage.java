package org.example.javareservationproject.infra.storage;

import java.util.Optional;

public interface SnapshotStorage<T> {

    Optional<Snapshot<T>> load(String storageKey, Class<T> entityType);

    void save(String storageKey, Snapshot<T> snapshot);
}