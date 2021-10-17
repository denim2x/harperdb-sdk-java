package io.harperdb;

import static com.alibaba.fastjson.JSON.toJSONString;
import io.harperdb.auth.Authentication;
import io.harperdb.base.Payload;
import io.harperdb.util.Strings;
import static io.harperdb.util.Strings.ANGLE_QUOTES;
import io.harperdb.util.Trace;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static io.harperdb.util.Streams.acquire;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import io.harperdb.base.Procedure;
import static io.harperdb.util.Preconditions.checkNotNull;
import static io.harperdb.util.Strings.embed;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class Processor implements Trace {

    public static final String JSON = "application/json";

    private final URL endpoint;
    protected final String authString;

    protected Processor(URL endpoint, Authentication auth) {
        this.endpoint = endpoint;
        authString = nonNull(auth) ? auth.yield() : null;
    }

    public URL endpoint() {
        return endpoint;
    }

    public Response fetch(Payload payload, Stream<Procedure> procs) {
        var body = makeJSON(payload);

        try {
            var service = tap(true);
            service.setRequestMethod(HttpMethod.POST.name());

            if (nonNull(authString)) {
                service.setRequestProperty("Authorization", authString);
            }

            service.setRequestProperty("Content-Type", JSON);
            service.setUseCaches(false);

            procs.peek(p -> p.setup(service));

            try (var out = service.getOutputStream()) {
                out.write(body.getBytes());
            }

            return new Response(service.getResponseCode(), Strings.collect(acquire(service)));
        } catch (IOException ex) {
            warn(ex, "Request failed: {0}", embed(ANGLE_QUOTES, body));
        }

        return new Response();
    }

    public Response fetch(Payload payload, Iterable<Procedure> procs) {
        checkNotNull("procs", procs);
        return fetch(payload, StreamSupport.stream(procs.spliterator(), false));
    }

    public Response fetch(Payload payload, Procedure... procs) {
        return fetch(payload, Arrays.stream(procs));
    }

    @Override
    public String declare() {
        return endpoint.toString();
    }

    protected String makeJSON(Payload payload) {
        return toJSONString(payload);
    }

    protected HttpURLConnection tap(boolean useOutput) throws IOException {
        HttpURLConnection remote = null;
        try {
            remote = (HttpURLConnection) endpoint.openConnection();
            remote.setDoOutput(useOutput);
            return remote;
        } finally {
            try {
                ofNullable(remote).ifPresent(HttpURLConnection::disconnect);
            } catch (Exception ex) {
                warn("Connection not closed");
            }
        }
    }

    protected enum HttpMethod {
        POST
    }

}
