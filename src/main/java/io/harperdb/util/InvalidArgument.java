package io.harperdb.util;

import static java.text.MessageFormat.format;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface InvalidArgument {

    public static IllegalArgumentException using(String format, Object... values) {
        return new IllegalArgumentException(format(format, values));
    }

    public static IllegalArgumentException using(Throwable cause, String format, Object... values) {
        return new IllegalArgumentException(format(format, values), cause);
    }

    public static IllegalArgumentException of(Throwable cause, String name) {
        return using(cause, "Invalid argument: {0}", name);
    }
}
