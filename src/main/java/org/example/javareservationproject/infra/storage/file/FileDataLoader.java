package org.example.javareservationproject.infra.storage.file;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.example.javareservationproject.infra.storage.Snapshot;

public final class FileDataLoader {

    private static final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static <T> Optional<Snapshot<T>> loadFromClasspath(Path path, Class<T> entityType) {
        try {
            if (!Files.exists(path)) {
                return Optional.empty();
            }

            try (InputStream is = Files.newInputStream(path)) {
                // 직렬화할 클래스 타입 지정
                TypeFactory typeFactory = mapper.getTypeFactory();
                JavaType snapshotType = typeFactory.constructParametricType(Snapshot.class, entityType);

                return Optional.ofNullable(mapper.readValue(is, snapshotType));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void saveToFile(Path path, Snapshot<T> snapShot) {
        try {
            Files.createDirectories(path.getParent());
            mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), snapShot);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
