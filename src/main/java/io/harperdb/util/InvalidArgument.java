package io.harperdb.util;

import static java.text.MessageFormat.format;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface InvalidArgument {

    public static IllegalArgumentException as(String format, Object... values) {
        return new IllegalArgumentException(format(format, values));
    }

    public static IllegalArgumentException as(Throwable cause, String format, Object... values) {
        return new IllegalArgumentException(format(format, values), cause);
    }

    public static IllegalArgumentException of(Throwable cause, String name) {
        return InvalidArgument.as(cause, "Invalid argument: {0}", name);
    }
}
