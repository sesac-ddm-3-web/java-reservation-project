package org.example.javareservationproject.infra.storage.support;

import java.util.List;

import org.example.javareservationproject.infra.storage.Snapshot;
import org.example.javareservationproject.infra.storage.SnapshotBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SnapshotRepositorySupport<T> {

    SnapshotBinding<T> binding();

    default void loadSnapshot() {
        Logger log = LoggerFactory.getLogger(this.getClass());

        SnapshotBinding<T> b = binding();
        b.storage().load(b.storageKey(), b.entityType())
            .ifPresent(snap -> {
                b.db().clear();
                b.db().addAll(snap.items());
                b.sequence().set(snap.sequence());
            });

        log.info("[Snapshot] Loaded '{}' data. (cnt={})", b.storageKey(), b.db().size());
    }

    default void writeSnapshot() {
        Logger log = LoggerFactory.getLogger(this.getClass());

        SnapshotBinding<T> b = binding();
        long seqValue = b.sequence().get();
        b.storage().save(b.storageKey(), new Snapshot<>(seqValue, List.copyOf(b.db())));

        log.info("[Snapshot] Saved '{}' data. (cnt={})", b.storageKey(), b.db().size());
    }
}
