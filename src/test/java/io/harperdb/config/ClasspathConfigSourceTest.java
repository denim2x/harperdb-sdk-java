package io.harperdb.config;

import io.harperdb.config.source.ClasspathConfigSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.util.Optional;

class ClasspathConfigSourceTest {

    @Test
    void testClasspathSource() {
        ClasspathConfigSource classpathConfigSource = new ClasspathConfigSource("harperdb.properties");
        Optional<Reader> load = classpathConfigSource.load();
        Assertions.assertTrue(load.isPresent());
    }

    @Test
    void testNonExistingClasspathSource() {
        ClasspathConfigSource classpathConfigSource = new ClasspathConfigSource("harper.properties");
        Optional<Reader> load = classpathConfigSource.load();
        Assertions.assertFalse(load.isPresent());
    }
}