package io.harperdb.util;

import static io.harperdb.util.Preconditions.checkNotNull;
import java.time.Duration;
import java.util.function.Function;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface TimeSpan {

    static final Function<Float, Integer> ROUNDING = Math::round;

    public static Duration ofSeconds(float seconds) {
        return ofSeconds(seconds * 1000, ROUNDING);
    }

    public static Duration ofSeconds(float seconds, Function<Float, Integer> rounding) {
        checkNotNull("rounding", rounding);
        return Duration.ofMillis(rounding.apply(seconds * 1000));
    }
}
