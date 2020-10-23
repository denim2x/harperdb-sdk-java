package io.harperdb.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigModelTest {

    @Test
    void testPutProperty() {
        ConfigModel configModel = ConfigModel.create();
        configModel.put("server.node1.host", "localhost");

        Assertions.assertTrue(configModel.get("server.node1.host").isPresent());
        Assertions.assertEquals("localhost", configModel.get("server.node1.host").get());
    }

    @Test
    void testPutMultiplePropertiesWithSimilarRoots() {
        ConfigModel configModel = ConfigModel.create();
        configModel.put("server.node1.host", "localhost");
        configModel.put("server.node1.port", 9956);
        configModel.put("server.somebool", true);

        Assertions.assertTrue(configModel.get("server.node1.host").isPresent());
        Assertions.assertTrue(configModel.get("server.node1.port").isPresent());
        Assertions.assertEquals("localhost", configModel.get("server.node1.host").get());
        Assertions.assertEquals(9956, configModel.get("server.node1.port").get());
        Assertions.assertEquals(true, configModel.get("server.somebool").get());

        Assertions.assertEquals("localhost", configModel.getAsString("server.node1.host"));
        Assertions.assertEquals(9956, configModel.getAsInt("server.node1.port"));
        Assertions.assertEquals(9956, configModel.getAsLong("server.node1.port"));
        Assertions.assertEquals(true, configModel.getAsBoolean("server.somebool"));
    }

    @Test
    void testPutMultiplePropertiesWithDifferentRoots() {
        ConfigModel configModel = ConfigModel.create();
        configModel.put("server.node1.host", "localhost");
        configModel.put("server.node1.port", 9956);
        configModel.put("server.node2.host", "127.0.0.1");
        configModel.put("server.node2.port", 9957);

        Assertions.assertTrue(configModel.get("server.node1.host").isPresent());
        Assertions.assertTrue(configModel.get("server.node1.port").isPresent());
        Assertions.assertTrue(configModel.get("server.node2.host").isPresent());
        Assertions.assertTrue(configModel.get("server.node2.port").isPresent());
        Assertions.assertEquals("localhost", configModel.get("server.node1.host").get());
        Assertions.assertEquals(9956, configModel.get("server.node1.port").get());
        Assertions.assertEquals("127.0.0.1", configModel.get("server.node2.host").get());
        Assertions.assertEquals(9957, configModel.get("server.node2.port").get());
    }

}