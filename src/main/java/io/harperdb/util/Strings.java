package io.harperdb.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static java.lang.reflect.Modifier.isPublic;
import static java.text.MessageFormat.format;
import static java.util.Arrays.stream;
import java.util.Objects;
import static java.util.Objects.isNull;
import java.util.stream.Collectors;
import static java.util.stream.Stream.concat;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface Strings {

    public static final String ANGLE_QUOTES = "\u2039{0}\u203a";

    public static String collect(InputStream stream) {
        if (isNull(stream)) {
            return null;
        }

        var reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().collect(Collectors.joining("\n"));
    }

    public static Eval embed(String format, Object... values) {
        return new Eval(format, values);
    }

    public static <T> String tell(String format, T value) {
        return embed(format, Strings.tell(value)).toString();
    }

    public static <T> String tell(T value) {
        if (isNull(value)) {
            return "null";
        }

        if (value instanceof Class<?>) {
            return asType((Class<?>) value);
        }

        var type = value.getClass();
        var methods = concat(stream(type.getDeclaredMethods()), stream(type.getMethods()))
                .distinct()
                .filter(m -> m.isAnnotationPresent(Explain.class) && isPublic(m.getModifiers()));

        var explained = methods.map(m -> {
            try {
                return (String) m.invoke(value);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                return null;
            }
        }).filter(Objects::nonNull).findFirst();

        return explained.orElse(value.toString());
    }

    static String asType(Class<?> type) {
        return type.getName().replace(type.getPackageName() + ".", "").replace('$', '.');
    }

    public static String tellFunction(Method method) {
        if (isNull(method)) {
            return null;
        }

        var ret = asType(method.getReturnType());
        var params = stream(method.getParameterTypes())
                .map(Strings::asType)
                .collect(Collectors.joining(", "));

        return embed("({1}) -> {0}", ret, params).toString();
    }

    public static class Eval {

        protected final String value;

        protected Eval(String format, Object... values) {
            value = isNull(format) ? null : format(format, values);
        }

        @Override
        public String toString() {
            return value;
        }

        public String using(String format) {
            if (isNull(format)) {
                return null;
            }

            return isNull(value) ? format : format(format, value);
        }
    }
}
