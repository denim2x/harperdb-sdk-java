package io.harperdb.base;

import static io.harperdb.util.Preconditions.checkNotNull;
import static io.harperdb.base.Stage.*;
import java.net.URLConnection;
import java.time.Duration;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface Attribute<T, E> {

    void to(T target, E extra);

    public static Attribute<?, ?> timeout(int stages, Duration value) {
        return new Timeout<Payload>(stages, value);
    }

    public class Timeout<E> implements Attribute<URLConnection, E> {

        protected final int stages;
        protected final int value;

        protected Timeout(int stages, Duration value) {
            checkNotNull(value);
            this.stages = stages;
            this.value = (int) value.toMillis();
        }

        @Override
        public void to(URLConnection target, E extra) {
            checkNotNull("target", target);
            if (CONNECT.check(stages)) {
                target.setConnectTimeout(value);
            }
            if (REQUEST.check(stages)) {
                target.setReadTimeout(value);
            }
        }

    }
}
