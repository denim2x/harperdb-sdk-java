package io.harperdb.config.parser;

import io.harperdb.config.ConfigModel;
import io.harperdb.config.source.ConfigSource;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import java.util.Properties;

public class PropertiesParser implements ConfigParser {

    @Override
    public ConfigModel parse(ConfigSource source) {
        Optional<Reader> optionalReader = source.load();
        ConfigModel configModel = ConfigModel.create();
        if (optionalReader.isPresent()) {
            Properties props = new Properties();
            try (Reader reader = optionalReader.get()) {
                props.load(reader);
                props.forEach((key, value) -> configModel.insert(key.toString(), value));
            } catch (IOException e) {
                //TODO Exception handling
            }
        }
        return configModel;
    }
}
