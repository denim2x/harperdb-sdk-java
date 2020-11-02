package io.harperdb.config.source;

import java.io.Reader;
import java.util.Optional;

public interface ConfigSource {

    Optional<Reader> load();

}
