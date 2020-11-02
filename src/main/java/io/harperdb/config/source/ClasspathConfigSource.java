package io.harperdb.config.source;

import io.harperdb.config.parser.ConfigParser;

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
        Objects.requireNonNull(config, "A property configuration is not provided");
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
            //TODO Exception handling, debug level log
        }
        return Optional.empty();
    }
}
