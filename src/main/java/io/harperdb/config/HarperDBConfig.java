package io.harperdb.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.harperdb.config.Configuration.classpath;

public class HarperDBConfig {

    private final ConfigModel configModel;

    private HarperDBConfig(ConfigModel configModel) {
        this.configModel = configModel;
    }

    public static HarperDBConfig create() {
        Builder builder = builder()
                .sources(
                        classpath("harperdb.properties"),
                        classpath("harperdb.yaml")
                );
        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Object get(String property) {
        return configModel.get(property).orElse(null);
    }

    public Object get(String property, Object defaultValue) {
        return configModel.get(property, defaultValue);
    }

    public Integer getAsInt(String property) {
        return configModel.getAsInt(property);
    }

    public Integer getAsInt(String property, Integer defaultValue) {
        return configModel.getAsInt(property, defaultValue);
    }

    public String getAsString(String property) {
        return configModel.getAsString(property);
    }

    public String getAsString(String property, String defaultValue) {
        return configModel.getAsString(property, defaultValue);
    }

    public Boolean getAsBoolean(String property) {
        return configModel.getAsBoolean(property);
    }

    public Boolean getAsBoolean(String property, Boolean defaultValue) {
        return configModel.getAsBoolean(property, defaultValue);
    }

    public static class Builder {

        List<Configuration> sourcesList = new ArrayList<>();

        public Builder sources(Configuration... sources) {
            sourcesList.addAll(Arrays.asList(sources));
            return this;
        }

        public HarperDBConfig build() {
            ConfigModel configModel = sourcesList.stream()
                    .map(configuration -> configuration.getParser().parse(configuration.getSource()))
                    .reduce(ConfigModel::merge)
                    .orElse(ConfigModel.create());

            return new HarperDBConfig(configModel);
        }
    }
}
