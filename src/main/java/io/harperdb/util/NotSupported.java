/*
 * Copyright (c)
 *
 */
package io.harperdb.util;

import static java.text.MessageFormat.format;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface NotSupported {

    public static Exception using(String format, Object... values) {
        return new Exception(format(format, values));
    }

    public static Exception using(Throwable cause, String format, Object... values) {
        return new Exception(format(format, values), cause);
    }

    public static Exception of(String value) {
        return using("Not supported: {0}", value);
    }

    public static Exception of(Throwable cause, String value) {
        return using(cause, "Not supported: {0}", value);
    }
}
