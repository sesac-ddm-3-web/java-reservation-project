package org.example.javareservationproject.infra.storage.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.example.javareservationproject.infra.storage.Snapshot;
import org.example.javareservationproject.infra.storage.SnapshotStorage;
import org.springframework.stereotype.Component;

@Component
public class FileSnapshotStorage<T> implements SnapshotStorage<T> {

    private final Path baseDir = Paths.get("data");

    private Path resolve(String key) {
        return baseDir.resolve(key + ".json");
    }

    @Override
    public Optional<Snapshot<T>> load(String key, Class<T> entityType) {
        return FileDataLoader.loadFromClasspath(resolve(key), entityType);
    }

    @Override
    public void save(String key, Snapshot<T> snapshot) {
        FileDataLoader.saveToFile(resolve(key), snapshot);
    }
}