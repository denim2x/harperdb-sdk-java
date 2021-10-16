package io.harperdb.util;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface Trace {

    static String useMessage(Trace instance, String format, Object... args) {
        var decl = instance.declare();
        var msg = format(format, args);
        return isNull(decl) ? msg : format("[{0}] {1}", decl, msg);
    }

    static Logger logger(Trace instance) {
        return Logger.getLogger(instance.getClass().getName());
    }

    default String declare() {
        return null;
    }

    default void openTrace(Handler handler) {
        logger(this).addHandler(handler);
    }

    default void stopTrace(Handler handler) {
        logger(this).removeHandler(handler);
    }

    default void trace(Level level, String format, Object... args) {
        logger(this).log(level, useMessage(this, format, args));
    }

    default void trace(Level level, Throwable error, String format, Object... args) {
        logger(this).log(level, useMessage(this, format, args), error);
    }

    default void info(String format, Object... args) {
        trace(Level.INFO, format, args);
    }

    default void warn(String format, Object... args) {
        trace(Level.WARNING, format, args);
    }

    default void warn(Throwable error, String format, Object... args) {
        trace(Level.WARNING, error, format, args);
    }

    default void error(String format, Object... args) {
        trace(Level.SEVERE, format, args);
    }

    default void error(Throwable error, String format, Object... args) {
        trace(Level.SEVERE, error, format, args);
    }
}
