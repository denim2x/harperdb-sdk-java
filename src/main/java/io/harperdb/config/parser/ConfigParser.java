package io.harperdb.config.parser;

import io.harperdb.config.ConfigModel;
import io.harperdb.config.ConfigParserException;
import io.harperdb.config.source.ConfigSource;

import java.util.Arrays;
import java.util.function.Supplier;

public interface ConfigParser {

    enum Parsers {
        PROPERTIES(PropertiesParser::new , "properties"),
        YAML(YamlParser::new, "yaml", "yml");

        private final Supplier<? extends ConfigParser> factory;
        private final String[] extensions;

        Parsers(Supplier<? extends ConfigParser> factory, String... extensions) {
            this.factory = factory;
            this.extensions = extensions;
        }

        public static ConfigParser createParser(String extension) {
            Parsers parser = Arrays.stream(values())
                    .filter(parsers -> Arrays.stream(parsers.extensions.clone()).anyMatch(s -> s.equalsIgnoreCase(extension)))
                    .findFirst()
                    .orElseThrow(() -> new ConfigParserException("Unsupported configuration type"));

            return parser.factory.get();
        }

    }

    ConfigModel parse(ConfigSource source);
}
