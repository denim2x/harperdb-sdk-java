package io.harperdb.config.parser;

import io.harperdb.config.ConfigModel;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ConfigParser {

    ConfigModel parse() throws URISyntaxException;

    ConfigModel parse(String path) throws URISyntaxException, IOException;
}
