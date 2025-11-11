package org.example.javareservationproject.infra.storage;

import java.util.Collection;

public record Snapshot<T>(
    long sequence,
    Collection<T> items
) {
}
