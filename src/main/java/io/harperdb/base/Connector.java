package io.harperdb.base;

import java.net.URI;
import java.time.Duration;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface Connector {

    URI endpoint() throws Throwable;

    Authorization authorization();

    Duration timeout();
}
