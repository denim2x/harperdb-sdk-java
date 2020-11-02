package io.harperdb.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigModelTest {

    @Test
    void testPutProperty() {
        ConfigModel configModel = ConfigModel.create();
        configModel.insert("server.node1.host", "localhost");

        Assertions.assertTrue(configModel.get("server.node1.host").isPresent());
        Assertions.assertEquals("localhost", configModel.get("server.node1.host").get());
    }

    @Test
    void testPutMultiplePropertiesWithSimilarRoots() {
        ConfigModel configModel = ConfigModel.create();
        configModel.insert("server.node1.host", "localhost");
        configModel.insert("server.node1.port", 9956);
        configModel.insert("server.somebool", true);

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
        configModel.insert("server.node1.host", "localhost");
        configModel.insert("server.node1.port", 9956);
        configModel.insert("server.node2.host", "127.0.0.1");
        configModel.insert("server.node2.port", 9957);

        Assertions.assertTrue(configModel.get("server.node1.host").isPresent());
        Assertions.assertTrue(configModel.get("server.node1.port").isPresent());
        Assertions.assertTrue(configModel.get("server.node2.host").isPresent());
        Assertions.assertTrue(configModel.get("server.node2.port").isPresent());
        Assertions.assertEquals("localhost", configModel.get("server.node1.host").get());
        Assertions.assertEquals(9956, configModel.get("server.node1.port").get());
        Assertions.assertEquals("127.0.0.1", configModel.get("server.node2.host").get());
        Assertions.assertEquals(9957, configModel.get("server.node2.port").get());
    }

    @Test
    void testMerge() {
        ConfigModel configModel = ConfigModel.create();
        configModel.insert("server.node1.host", "localhost");
        configModel.insert("server.node1.port", 9956);
        configModel.insert("server.node2.host", "127.0.0.1");
        configModel.insert("server.node2.port", 9957);

        ConfigModel configModel2 = ConfigModel.create();
        configModel2.insert("server.node1.host", "192.168.10.12");
        configModel2.insert("server.node1.port", 8800);
        configModel2.insert("server.test.config", "test");

        ConfigModel merged = configModel.merge(configModel2);

        Assertions.assertTrue(merged.get("server.node1.host").isPresent());
        Assertions.assertTrue(merged.get("server.node1.port").isPresent());
        Assertions.assertTrue(merged.get("server.node2.host").isPresent());
        Assertions.assertTrue(merged.get("server.node2.port").isPresent());
        Assertions.assertEquals("192.168.10.12", merged.get("server.node1.host").get());
        Assertions.assertEquals(8800, merged.get("server.node1.port").get());
        Assertions.assertEquals("127.0.0.1", merged.get("server.node2.host").get());
        Assertions.assertEquals(9957, merged.get("server.node2.port").get());
    }

}