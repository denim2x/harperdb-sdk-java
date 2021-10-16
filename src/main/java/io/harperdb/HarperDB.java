package io.harperdb;

import io.harperdb.util.InvalidArgument;
import static io.harperdb.util.Preconditions.checkNotNull;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import io.harperdb.auth.Authentication;
import io.harperdb.base.Attribute;
import io.harperdb.util.NotSupported;
import static io.harperdb.util.Strings.ANGLE_QUOTES;
import static io.harperdb.util.Strings.eval;
import static io.harperdb.util.Strings.tell;
import io.harperdb.util.Trace;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.util.function.Predicate;
import static java.util.regex.Pattern.compile;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class HarperDB<A extends Authentication> implements Trace {

    public static final int PORT = 9925;
    protected static final Predicate<String> PROTOCOL = compile("^https?$").asPredicate();

    private final URL endpoint;
    private final A auth;

    protected HarperDB(URL endpoint, A auth) {
        check(endpoint);
        this.endpoint = endpoint;

        if (isNull(auth)) {
            info("Authentication: {0}", tell(ANGLE_QUOTES, auth));
        }
        this.auth = auth;
    }

    public static HarperDB<?> open(URL endpoint) {
        checkNotNull("endpoint", endpoint);
        return new HarperDB<>(endpoint, Authentication.basic());
    }

    public static HarperDB<?> open(URI endpoint) throws IllegalArgumentException {
        checkNotNull("endpoint", endpoint);
        return open(endpoint.getScheme(), endpoint.getHost(), endpoint.getPort());
    }

    public static HarperDB<?> open(String endpoint) throws IllegalArgumentException {
        checkNotNull("endpoint", endpoint);

        try {
            return open(new URL(endpoint));
        } catch (MalformedURLException ex) {
            throw InvalidArgument.of(ex, "endpoint");
        }
    }

    public static HarperDB<?> open(String host, int port) throws IllegalArgumentException {
        return open(null, host, port);
    }

    public static HarperDB<?> open(String protocol, String host, int port) throws IllegalArgumentException {
        checkNotNull("host", host);

        if (isNull(protocol)) {
            protocol = "http";
        }

        try {
            return open(new URL(protocol, host, port, ""));
        } catch (MalformedURLException ex) {
            throw InvalidArgument.using(ex, "Endpoint {0} cannot be valid", eval("{0}://{1}:{2,number,#}", protocol, host, port).using(ANGLE_QUOTES));
        }
    }

    public final URL endpoint() {
        return endpoint;
    }

    public final A authentication() {
        return auth;
    }

    public Processor apply(Attribute... attrs) {
        return new Processor(endpoint, auth).apply(attrs);
    }

    @Override
    public String declare() {
        return endpoint.toString();
    }

    protected void check(URL endpoint) {
        Throwable cause = null;

        if (!PROTOCOL.test(endpoint.getProtocol())) {
            cause = NotSupported.of("protocol");
        }

        if (nonNull(cause)) {
            throw InvalidArgument.of(cause, "endpoint");
        }
    }
}
