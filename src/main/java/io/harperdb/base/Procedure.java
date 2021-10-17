package io.harperdb.base;

import static io.harperdb.util.Preconditions.checkNotNull;
import static io.harperdb.base.Protocol.*;
import java.net.URLConnection;
import java.time.Duration;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface Procedure {

    void setup(URLConnection service);

    public static Procedure timeout(int protocol, Duration value) {
        return new Timeout(protocol, value);
    }

    public class Timeout implements Procedure {

        protected final int protocol;
        protected final int value;

        protected Timeout(int protocol, Duration value) {
            checkNotNull("value", value);
            this.protocol = protocol;
            this.value = (int) value.toMillis();
        }

        @Override
        public void setup(URLConnection service) {
            checkNotNull("service", service);
            if (CONNECT.check(protocol)) {
                service.setConnectTimeout(value);
            }
            if (REQUEST.check(protocol)) {
                service.setReadTimeout(value);
            }
        }

    }
}
