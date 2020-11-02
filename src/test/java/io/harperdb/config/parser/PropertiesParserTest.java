package io.harperdb.config.parser;

import io.harperdb.config.ConfigModel;
import io.harperdb.config.source.ClasspathConfigSource;
import io.harperdb.config.source.ConfigSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesParserTest {

    @Test
    void testParse_ValidFile() {
        PropertiesParser propertiesParser = new PropertiesParser();
        ConfigSource source = new ClasspathConfigSource("harperdb.properties");
        ConfigModel configModel = propertiesParser.parse(source);

        assertNotNull(configModel);
        assertEquals(9956, configModel.getAsInt("server.node1.port"));
        assertEquals("localhost", configModel.getAsString("server.node1.host"));
        assertEquals(9957, configModel.getAsInt("server.node2.port"));
        assertEquals("127.0.0.1", configModel.getAsString("server.node2.host"));
    }

    @Test
    void testParse_NonExistingFile() {
        PropertiesParser propertiesParser = new PropertiesParser();
        ConfigSource source = new ClasspathConfigSource("harperdb1.properties");
        ConfigModel configModel = propertiesParser.parse(source);

        assertNotNull(configModel);
        assertTrue(configModel.getAsMap().isEmpty());
    }
}