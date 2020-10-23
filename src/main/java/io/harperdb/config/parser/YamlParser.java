package io.harperdb.config.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.harperdb.config.ConfigModel;
import io.harperdb.config.ConfigParserException;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class YamlParser implements ConfigParser {

    public static final String HARPERDB_YML = "harperdb.yml";
    public static final String HARPERDB_YAML = "harperdb.yaml";

    @Override
    public ConfigModel parse() {
        String config = configYamlExists();
        return parse(config);
    }

    private String configYamlExists() {
        try {
            URI yml = ClassLoader.getSystemResource(HARPERDB_YML).toURI();
            URI yaml = ClassLoader.getSystemResource(HARPERDB_YAML).toURI();
            if (Paths.get(yaml).toFile().exists()) {
                return HARPERDB_YAML;
            } else if (Paths.get(yml).toFile().exists()) {
                return HARPERDB_YML;
            } else {
                throw new ConfigParserException("Configuration not found");
            }
        } catch (URISyntaxException e) {
            throw new ConfigParserException("Config location URI has an invalid format", e);
        }
    }

    @Override
    public ConfigModel parse(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return null;
    }
}
