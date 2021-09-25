package io.harperdb.base;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class Opaque<V, C extends Throwable> {
    private final V value;
    private final C cause;

    protected Opaque(V value, C cause) {
        this.value = value;
        this.cause = cause;
    }

    public static <V, C extends Throwable> Opaque<V, C> of(V value) {
        return new Opaque<>(value, null);
    }

    public static <V, C extends Throwable> Opaque<V, C> causing(C cause) {
        return new Opaque<>(null, cause);
    }

    public boolean isValid() {
        return isNull(cause);
    }

    public boolean nonValid() {
        return nonNull(cause);
    }

    public V value() throws C {
        if (nonValid()) {
            throw cause();
        }

        return value;
    }

    public C cause() {
        return cause;
    }

    public V expect(V value) {
        return isValid() ? this.value : value;
    }

    public Opaque<V, C> then(Consumer<V> fn) {
        if (isValid()) {
            fn.accept(value);
        }

        return this;
    }

    public Opaque<V, C> then(BiConsumer<V, C> fn) {
        fn.accept(value, cause);
        return this;
    }

    public Opaque<V, C> except(Consumer<C> fn) {
        if (nonValid()) {
            fn.accept(cause);
        }

        return this;
    }

}
