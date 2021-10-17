package io.harperdb.test;

import com.tngtech.jgiven.format.ArgumentFormatter;
import static io.harperdb.util.Strings.ANGLE_QUOTES;
import java.time.Duration;
import static java.util.Objects.isNull;
import static io.harperdb.util.Strings.tell;
import static io.harperdb.util.Strings.tell;
import static io.harperdb.util.Strings.tell;
import static io.harperdb.util.Strings.tell;
import static io.harperdb.util.Strings.tell;
import static io.harperdb.util.Strings.tell;
import static io.harperdb.util.Strings.tell;
import static io.harperdb.util.Strings.tell;
import io.harperdb.util.Explain;
import static io.harperdb.util.Strings.embed;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class GenericFormatter implements ArgumentFormatter<Object> {

    @Override
    public String format(Object value, String... strings) {
        if (isNull(value)) {
            return tell(ANGLE_QUOTES, value);
        }

        if (value instanceof String) {
            return tell("\"{0}\"", value);
        }

        if (value instanceof Class<?>) {
            return tell(ANGLE_QUOTES, value);
        }

        var type = value.getClass();
        String body;
        if (value instanceof Duration) {
            body = format((Duration) value);
        } else {
            body = tell(value);
        }

        if (value instanceof Number) {
            return body;
        }

        if (type.isAnnotationPresent(Explain.class)) {
            return body;
        }

        return embed("{0} '{'{1}'}'", tell(type), body).using(ANGLE_QUOTES);

    }

    String format(Duration value) {
        var hours = value.toHours();
        var mins = value.toMinutesPart();
        var secs = value.toSecondsPart();

        return embed("{0,number,#}:{1,number,00}:{2,number,00}", hours, mins, secs).toString();
    }

}
