package io.harperdb.config.parser;

import io.harperdb.config.ConfigParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigParserTest {

    @Test
    void parserResolver_ValidParser() {
        Assertions.assertEquals(PropertiesParser.class, ConfigParser.Parsers.createParser("properties").getClass());
    }

    @Test
    void parserResolver_MultipleExtensionSupport() {
        Assertions.assertEquals(YamlParser.class, ConfigParser.Parsers.createParser("yaml").getClass());
        Assertions.assertEquals(YamlParser.class, ConfigParser.Parsers.createParser("yml").getClass());
    }

    @Test
    void parserResolver_NotSupportedExtension() {
        ConfigParserException exception = Assertions.assertThrows(ConfigParserException.class, () -> ConfigParser.Parsers.createParser("conf"));
        Assertions.assertEquals("Unsupported configuration type", exception.getMessage());
    }
}