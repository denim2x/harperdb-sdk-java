package io.harperdb.config;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

public class ClasspathConfigSource implements ConfigSource {

    private final String config;

    public ClasspathConfigSource(String config) {
        this.config = config;
    }

    @Override
    public Optional<Reader> load() {
        try {
            URL systemResource = Thread.currentThread().getContextClassLoader().getResource(config);
            if (Objects.nonNull(systemResource)) {
                URI uri = systemResource.toURI();
                Path path = Paths.get(uri);
                return Optional.of(Files.newBufferedReader(path));
            }
        } catch (URISyntaxException | IOException e) {
            //TODO debug level log
        }
        return Optional.empty();
    }
}
