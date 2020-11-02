package io.harperdb.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A Common representation for different configuration options i.e. properties, yml
 */
public class ConfigModel {

    private final Map<String, Object> configurations = new HashMap<>();

    private ConfigModel() {
    }

    public static ConfigModel create() {
        return new ConfigModel();
    }

    public Object insert(String property, Object value) {
        return configurations.put(property, value);
    }

    public Optional<Object> get(String property) {
        return Optional.ofNullable(configurations.get(property));
    }

    public Object get(String property, Object defaultValue) {
        return this.get(property).orElse(defaultValue);
    }

    public String getAsString(String property) {
        return getAsString(property, null);
    }

    public String getAsString(String property, String defaultValue) {
        return get(property).map(Object::toString).orElse(defaultValue);
    }

    public Integer getAsInt(String property) {
        return getAsInt(property, null);
    }

    public Integer getAsInt(String property, Integer defaultValue) {
        String prop = getAsString(property);
        if (Objects.nonNull(prop)) {
            return Integer.valueOf(prop);
        }
        return defaultValue;
    }

    public Long getAsLong(String property) {
        return getAsLong(property, null);
    }

    public Long getAsLong(String property, Long defaultValue) {
        String prop = getAsString(property);
        if (Objects.nonNull(prop)) {
            return Long.valueOf(prop);
        }
        return defaultValue;
    }

    public Boolean getAsBoolean(String property) {
        return getAsBoolean(property, null);
    }

    public Boolean getAsBoolean(String property, Boolean defaultValue) {
        String prop = getAsString(property);
        if (Objects.nonNull(prop)) {
            return Boolean.valueOf(prop);
        }
        return defaultValue;
    }

    public ConfigModel merge(ConfigModel other) {
        Map<String, Object> otherAsMap = other.getAsMap();
        otherAsMap.forEach(this::insert);
        return this;
    }

    public Map<String, Object> getAsMap() {
        return new HashMap<>(configurations);
    }

}
