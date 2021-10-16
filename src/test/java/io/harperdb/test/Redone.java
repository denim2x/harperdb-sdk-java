package io.harperdb.test;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class Redone<V> {
    private final V value;
    private final String aux;

    private Redone(V value, String aux) {
        this.value = value;
        this.aux = aux;
    }

    public static <V> Value<V> redo(V base) {
        return new Value<>(base);
    }

    public static Value<String> redo(String format, Object... values) {
        return redo(format(format, values));
    }

    @Override
    public String toString() {
        return aux;
    }

    @Override
    public boolean equals(Object other) {
        if (isNull(value)) {
            return isNull(other);
        }

        return value.equals(other);
    }

    public static final class Value<V> {
        private final V value;

        private Value(V value) {
            this.value = value;
        }

        public Redone<V> as(String format, Object... values) {
            return new Redone<>(value, format(format, values));
        }
    }
}
