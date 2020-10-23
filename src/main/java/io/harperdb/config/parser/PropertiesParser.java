package io.harperdb.config.parser;

import io.harperdb.config.ConfigModel;
import io.harperdb.config.ConfigParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesParser implements ConfigParser {

    @Override
    public ConfigModel parse() {
        return parse("harperdb.properties");
    }

    @Override
    public ConfigModel parse(String config) throws ConfigParserException {
        //TODO Maybe Thread.currentThread().getContextClassLoader().getResource(resourceName).toURI()
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(config).toURI()))) {
            Properties properties = new Properties();
            properties.load(reader);

            ConfigModel configModel = ConfigModel.create();
            properties.forEach((key, value) -> configModel.put(key.toString(), value));

            return configModel;
        } catch (URISyntaxException e) {
            throw new ConfigParserException("Config location URI has an invalid format", e);
        } catch (IOException e) {
            throw new ConfigParserException("Reading configs failed from " + config, e);
        }
    }

}
