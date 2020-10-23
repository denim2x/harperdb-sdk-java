package io.harperdb.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A Common representation for different configuration options i.e. properties, yml
 */
public class ConfigModel {

    private ConfigModel() {
    }

    private final Node root = new Node(Empty.create());

    public static ConfigModel create() {
        return new ConfigModel();
    }

    public Object put(String property, Object value) {
        String[] tokens = property.split("\\.");
        Node current = root;
        for (int i = 0; i < tokens.length; i++) {
            if (i == tokens.length - 1) {
                current.add(tokens[i], value);
                return value;
            }
            current = current.add(tokens[i], Empty.create());
        }
        return null;
    }

    public Optional<Object> get(String property) {
        String[] tokens = property.split("\\.");
        Node current = root;
        for (int i = 0; i <= tokens.length; i++) {
            //If the last token is reached, the value of the property exists
            if (i == tokens.length) {
                return Optional.of(current.getValue());
            }
            //Try to get the next node in the tree
            current = current.getChildren().get(tokens[i]);
            //Break the flow if the next token is not found
            if (Objects.isNull(current)) {
                return Optional.empty();
            }
        }
        return Optional.empty();
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

    static class Node {
        private final Map<String, Node> children = new HashMap<>();
        private Object value;

        public Node(Object value) {
            this.value = value;
        }

        public Map<String, Node> getChildren() {
            return children;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Node add(String token,  Object value) {
            Node node = children.getOrDefault(token, new Node(value));
            children.put(token, node);
            return node;
        }
    }

    static final class Empty {
        private Empty() {
        }

        static Empty create() {
            return new Empty();
        }
    }

}
