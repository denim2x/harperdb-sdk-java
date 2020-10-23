package io.harperdb.config;

import java.io.Reader;
import java.util.Optional;

public interface ConfigSource {

    Optional<Reader> load();

}
