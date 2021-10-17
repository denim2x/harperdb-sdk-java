package io.harperdb;

import io.harperdb.util.InvalidArgument;
import static io.harperdb.util.Preconditions.checkNotNull;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import io.harperdb.auth.Authentication;
import static io.harperdb.util.Strings.ANGLE_QUOTES;
import static java.util.Objects.isNull;
import java.util.function.Predicate;
import static java.util.regex.Pattern.compile;
import io.harperdb.util.Cause;
import static io.harperdb.util.Strings.embed;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class HarperDB<A extends Authentication> {

    public static final int PORT = 9925;
    protected static final Predicate<String> PROTOCOL = compile("^https?$").asPredicate();

    private final URL endpoint;
    private final A auth;

    protected HarperDB(URL endpoint, A auth) {
        checkProtocol("endpoint", endpoint.getProtocol());
        this.endpoint = endpoint;
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
        } catch (MalformedURLException cause) {
            throw InvalidArgument.as(cause,
                    "{0} was invalid",
                    embed("{0}://{1}:{2,number,#}", protocol, host, port).using(ANGLE_QUOTES));
        }
    }

    public final URL endpoint() {
        return endpoint;
    }

    public final A authentication() {
        return auth;
    }

    public Processor processor() {
        return new Processor(endpoint, auth);
    }

    protected void checkProtocol(String name, String value) {
        if (!PROTOCOL.test(value)) {
            throw InvalidArgument.of(Cause.by("protocol"), name);
        }
    }
}
