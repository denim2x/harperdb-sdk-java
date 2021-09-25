package io.harperdb;

import io.harperdb.base.Authorization;
import io.harperdb.base.Connector;
import io.harperdb.base.Opaque;
import io.harperdb.base.Promise;
import io.harperdb.http.Session;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.time.Duration;
import static java.util.Objects.nonNull;

public final class HarperDB implements Connector {

    public static final int PORT = 9925;
    public static final Duration TIMEOUT = Duration.ofSeconds(10);

    Endpoint<?> endpoint;
    Authorization auth;
    Duration timeout = TIMEOUT;

    public HarperDB() {
        useEndpoint("localhost", PORT);
    }

    public Endpoint<?> useEndpoint(URI value) {
        if (nonNull(value)) {
            useEndpoint(value.getScheme(), value.getHost(), value.getPort());
        }

        return this.endpoint;
    }

    public Endpoint<?> useEndpoint(URL value) {
        if (nonNull(value)) {
            useEndpoint(value.getProtocol(), value.getHost(), value.getPort());
        }

        return this.endpoint;
    }

    public Endpoint<?> useEndpoint(String value) {
        if (nonNull(value)) {
            try {
                useEndpoint(new URI(value));
            } catch (URISyntaxException ex) {
                useEndpoint(null, ex);
            }
        }

        return this.endpoint;
    }

    public Endpoint<?> useEndpoint(String host, int port) {
        useEndpoint(null, host, port);
        return this.endpoint;
    }

    @Override
    public URI endpoint() throws IllegalStateException {
        if (endpoint.nonValid()) {
            throw new IllegalStateException("Provided endpoint is invalid", endpoint.cause());
        }

        return endpoint.expect(null);
    }

    public HarperDB withAuthorization(Authorization auth) {
        this.auth = auth;
        return this;
    }

    @Override
    public Authorization authorization() {
        return auth;
    }

    public HarperDB withTimeout(int timeout) {
        return withTimeout(Duration.ofSeconds(timeout));
    }

    public HarperDB withTimeout(Duration timeout) {
        if (nonNull(timeout)) {
            this.timeout = timeout;
        }

        return this;
    }

    /**
     * Retrieves the default connection timeout.
     *
     * @return The current timeout isValue
     */
    @Override
    public Duration timeout() {
        return timeout;
    }

    public Opaque<Session<? extends Promise<? extends Result<String>>>, ? extends Throwable>
            open() {
        return open(HttpClient.newBuilder());
    }

    public Opaque<Session<? extends Promise<? extends Result<String>>>, ? extends Throwable>
            open(HttpClient.Builder http) {
        try {
            return Opaque.of(new SessionImpl(http.build(), this));
        } catch (Throwable ex) {
            return Opaque.causing(ex);
        }
    }

    void useEndpoint(String scheme, String host, int port) {
        try {
            useEndpoint(new URI(nonNull(scheme) ? scheme : "http", null, host, port, null, null, null), null);
        } catch (URISyntaxException ex) {
            useEndpoint(null, ex);
        }
    }

    <E extends Exception> void useEndpoint(URI value, E error) {
        endpoint = new Endpoint<>(value, error);
    }

    public final class Endpoint<E extends Exception> extends Opaque<URI, E> {

        public Endpoint(URI value, E error) {
            super(value, error);
        }

        public Endpoint<E> with(String scheme) {
            var endpoint = expect(null);
            if (nonNull(endpoint)) {
                useEndpoint(scheme, endpoint.getHost(), endpoint.getPort());
            }

            return this;
        }
    }
}
