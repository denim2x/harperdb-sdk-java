package io.harperdb.util;

import com.ibm.icu.text.MessageFormat;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isProtected;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.stream;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class Utils {

    static final Map<Class<?>, Map<String, Function<Object, Object>>> _invokables = new HashMap<>();

    Utils() {
    }

    public static <T> String inspect(T value) {
        if (isNull(value)) {
            return "null";
        }

        @SuppressWarnings("unchecked")
        var type = (Class<T>) value.getClass();

        var formatter = getInspector(type);
        if (isNull(formatter)) {
            return value.toString();
        }

        var invokables = _invokables.computeIfAbsent(type, (k) -> collectInvokables(k, formatter.getArgumentNames()));
        var strings = collectStrings(value, invokables);
        return formatter.format(strings, new StringBuffer(), null).toString();
    }

    public static <T> Map<String, String> collectStrings(T value, Map<String, Function<T, Object>> invokables) {
        if (isNull(value)) {
            return null;
        }

        requireNonNull(invokables);

        var strings = new HashMap<String, String>();

        invokables.forEach((name, fn) -> {
            var ret = fn.apply(value);
            strings.put(name, nonNull(ret) ? ret.toString() : "");
        });

        return strings;
    }

    public static Map<String, Function<Object, Object>> collectInvokables(Class<?> type, Iterable<String> args) {
        if (isNull(type)) {
            return null;
        }

        requireNonNull(args);

        var invokables = new HashMap<String, Function<Object, Object>>();
        for (var arg : args) {
            final Method method = findMethod(type, arg);
            final Field field = findField(type, arg);

            if (isNull(method) && isNull(field)) {
                invokables.put(arg, o -> "");
            }

            invokables.put(arg, o -> {
                Object res = null;
                if (nonNull(method)) {
                    try {
                        res = method.invoke(isStatic(method.getModifiers()) ? null : o);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        res = null;
                    }
                }

                if (isNull(res) && nonNull(field)) {
                    try {
                        res = field.get(isStatic(field.getModifiers()) ? null : o);
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        res = null;
                    }
                }

                return res;
            });
        }

        return invokables;
    }

    public static <T> MessageFormat getInspector(Class<T> type) {
        if (isNull(type)) {
            return null;
        }

        var caption = getInspection(type);
        if (isNull(caption)) {
            return null;
        }

        var pattern = caption.value();
        if (isNull(pattern)) {
            return null;
        }

        return new MessageFormat(pattern);
    }

    public static <T> Inspection getInspection(Class<T> type) {
        return type.getAnnotation(Inspection.class);
    }

    public static Inspection getInspection(Method method) {
        if (isNull(method)) {
            return null;
        }

        return method.getAnnotation(Inspection.class);
    }

    public static Inspection getCaption(Field field) {
        if (isNull(field)) {
            return null;
        }

        return field.getAnnotation(Inspection.class);
    }

    static <T> Field findField(Class<T> type, String name) {
        if (isNull(type)) {
            return null;
        }

        try {
            return stream(type.getFields())
                    .filter(f -> f.getName().equals(name))
                    .sorted((a, b) -> compareInvokables(a.getModifiers(), b.getModifiers()))
                    .findFirst().orElse(null);
        } catch (SecurityException ex) {
            return null;
        }
    }

    static <T> Method findMethod(Class<T> type, String name) {
        if (isNull(type)) {
            return null;
        }

        try {
            return stream(type.getMethods())
                    .filter(m -> m.getName().equals(name) && getParameterCount(m) == 1)
                    .sorted((a, b) -> compareInvokables(a.getModifiers(), b.getModifiers()))
                    .findFirst().orElse(null);
        } catch (SecurityException ex) {
            return null;
        }
    }

    static int getParameterCount(Method method) {
        if (isStatic(method.getModifiers())) {
            return method.getParameterCount();
        }

        return method.getParameterCount() + 1;
    }

    static int compareInvokables(int a, int b) {
        if (isPrivate(b)) {
            return -1;
        }

        if (isProtected(b)) {
            return isPrivate(a) ? 1 : -1;
        }

        return isStatic(a) ? -1 : 1;
    }

}
