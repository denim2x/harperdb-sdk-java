package io.harperdb.config;

import io.harperdb.HarperDB;
import org.junit.jupiter.api.Test;

import static io.harperdb.config.Configuration.classpath;
import static org.junit.jupiter.api.Assertions.*;

class HarperDBConfigTest {

    @Test
    void loadsDefaultConfigurationAtCreation() {
        HarperDBConfig config = HarperDBConfig.create();

        assertNotNull(config);
        assertEquals("localhost", config.getAsString("server.node1.host"));
        assertEquals(9956, config.getAsInt("server.node1.port"));
        assertEquals("admin", config.getAsString("server.node1.username"));
        assertEquals("somePass!", config.getAsString("server.node1.password"));
        assertEquals("127.0.0.1", config.getAsString("server.node2.host"));
        assertEquals(9957, config.getAsInt("server.node2.port"));
    }

    @Test
    void canLoadConfigurationUsingBuilder() {
        HarperDBConfig config = HarperDBConfig.builder()
                .sources(
                        classpath("harperdb_custom.properties")
                ).build();

        assertNotNull(config);
        assertEquals("localhost", config.getAsString("server.node1.host"));
        assertEquals(9956, config.getAsInt("server.node1.port"));
    }

    @Test
    void loadNonExistingConfiguration() {
        HarperDBConfig config = HarperDBConfig.builder()
                .sources(
                        classpath("harperdb_custom.yaml")
                ).build();

        assertNotNull(config);
        assertNull(config.getAsString("server.node2.host"));
    }
}