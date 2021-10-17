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
public interface Cause {

    static final String NOT_SUPPORTED = "{0} not supported";

    public static Exception as(String format, Object... values) {
        return new Exception(format(format, values));
    }

    public static Exception as(Throwable cause, String format, Object... values) {
        return new Exception(format(format, values), cause);
    }

    public static Exception by(String value) {
        return Cause.as(NOT_SUPPORTED, value);
    }

    public static Exception of(Throwable cause, String value) {
        return as(cause, NOT_SUPPORTED, value);
    }
}
