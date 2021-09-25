package io.harperdb.http;

import io.harperdb.base.Payload;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.time.Duration;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public abstract class Session<R> {

    public static String AUTHORIZATION = "Authorization";
    public static String JSON = "application/json";

    public R submit(Payload payload, Duration timeout) {
        requireNonNull(timeout);
        return submit(usePayload(payload, timeout).build());
    }

    public R submit(Payload payload, int timeout) {
        return submit(payload, Duration.ofSeconds(timeout));
    }

    public R submit(Payload payload) {
        return submit(usePayload(payload, getTimeout()).build());
    }

    public abstract Duration getTimeout();

    protected abstract R submit(HttpRequest req);

    protected HttpRequest.Builder compose(BodyPublisher body, String contentType) {
        var req = HttpRequest.newBuilder();

        if (nonNull(contentType)) {
            req.header("Content-Type", contentType);
        }

        if (nonNull(body)) {
            req.POST(body);
        }

        return req;
    }

    protected HttpRequest.Builder authorize(HttpRequest.Builder req, String authToken) {
        if (nonNull(authToken)) {
            req.header(AUTHORIZATION, authToken);
        }

        return req;
    }

    protected abstract HttpRequest.Builder usePayload(Payload payload, Duration timeout);

}
