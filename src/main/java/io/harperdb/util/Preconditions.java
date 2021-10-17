package io.harperdb.util;

import static io.harperdb.util.Strings.ANGLE_QUOTES;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static io.harperdb.util.Strings.embed;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface Preconditions {

    public static <T> T checkNotNull(T value) {
        if (isNull(value)) {
            throw InvalidArgument.as("Argument was null");
        }

        return value;
    }

    public static Named checkNotNull(String name, Object value) {
        var named = new Named(name);

        if (isNull(value)) {
            throw InvalidArgument.as("Argument {0} was null", embed(named.name()).using(ANGLE_QUOTES));
        }

        return named;
    }

    /**
     *
     * @author denim2x <denim2x@cyberdude.com>
     */
    public static class Named {

        private final String name;

        Named(String name) {
            this.name = nonNull(name) ? name : "";
        }

        public String name() {
            return name;
        }

        public <T> T checkNotNull(String property, T value) {
            if (isNull(value)) {
                throw InvalidArgument.as("{0} was null", embed("{0}.{1}", name, property).using(ANGLE_QUOTES));
            }

            return value;
        }

    }
}
