package io.harperdb;

import static com.alibaba.fastjson.JSON.toJSONString;
import io.harperdb.base.Connector;
import io.harperdb.base.Pending;
import io.harperdb.base.Payload;
import io.harperdb.base.Promise;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import io.harperdb.http.Session;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class SessionImpl extends Session<Promise<Result<String>>> {

    protected final URI endpoint;
    protected final HttpClient http;
    protected String authToken = null;
    protected Duration timeout;

    public SessionImpl(HttpClient http, Connector connector) throws Throwable {
        requireNonNull(http);
        requireNonNull(connector);

        this.http = http;

        endpoint = connector.endpoint();
        requireNonNull(endpoint);

        var auth = connector.authorization();
        if (nonNull(auth)) {
            authToken = auth.yield();
        }

        timeout = connector.timeout();
    }

    @Override
    public Duration getTimeout() {
        return timeout;
    }

    @Override
    protected Promise<Result<String>> submit(HttpRequest req) {
        var res = http.sendAsync(req, BodyHandlers.ofString());
        return Pending.of(res, this::resolve);
    }

    @Override
    protected HttpRequest.Builder usePayload(Payload payload, Duration timeout) {
        var json = toJSONString(payload);
        var body = BodyPublishers.ofString(json);
        var req = compose(body, JSON).uri(endpoint);
        if (nonNull(timeout)) {
            req.timeout(timeout);
        }
        return authorize(req, authToken);
    }

    protected Result<String> resolve(HttpResponse<String> source) {
        var result = Result.create(source);
        if (nonNull(result)) {

        }

        return result;
    }

}
