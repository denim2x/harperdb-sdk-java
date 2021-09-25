package io.harperdb.test;

import com.tngtech.jgiven.format.ArgumentFormatter;
import io.harperdb.util.Utils;
import java.text.Format;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.ArrayList;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class GenericFormatter implements ArgumentFormatter<Object> {

    @Override
    public String format(Object value, String... strings) {
        if (strings.length > 0) {
            var args = new ArrayList<String>();
            stream(strings).skip(1).forEach(args::add);
            args.add(raw(value));
            return MessageFormat.format(strings[0], args.toArray());
        }

        if (value instanceof Class<?>) {
            return instance(((Class<?>) value).getSimpleName());
        }

        if (value instanceof String) {
            return string(value);
        }

        String body;
        if (value instanceof Duration) {
            body = duration((Duration) value);
        } else {
            body = Utils.inspect(value);
        }

        if (value instanceof Number) {
            return body;
        }

        if (isNull(value)) {
            return instance(body);
        }

        var type = value.getClass().getSimpleName();
        var format = MessageFormat.format("{0} '{'{1}'}'", type, body);
        return instance(format);
    }

    String raw(Object value) {
        var string = Utils.inspect(value);
        return nonNull(value) ? string : instance(string);
    }

    String string(Object value) {
        return MessageFormat.format("\"{0}\"", value);
    }

    String instance(Object value) {
        return MessageFormat.format("\u2039{0}\u203a", value);
    }

    String duration(Duration value) {
        var hours = value.toHours();
        var mins = value.toMinutesPart();
        var secs = value.toSecondsPart();

        return MessageFormat.format("{0,number,#}:{1,number,00}:{2,number,00}", hours, mins, secs);
    }

}
