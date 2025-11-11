package org.example.javareservationproject.infra.storage;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public record SnapshotBinding<T>(
    Class<T> entityType,
    AtomicLong sequence,
    Collection<T> db,
    String storageKey,
    SnapshotStorage<T> storage
) {
}