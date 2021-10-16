package io.harperdb;

import static com.alibaba.fastjson.JSON.toJSONString;
import io.harperdb.auth.Authentication;
import io.harperdb.base.Attribute;
import io.harperdb.base.Payload;
import io.harperdb.util.Strings;
import static io.harperdb.util.Strings.ANGLE_QUOTES;
import static io.harperdb.util.Strings.eval;
import io.harperdb.util.Trace;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static java.util.Arrays.stream;
import java.util.HashSet;
import java.util.Objects;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import java.util.Set;
import static io.harperdb.util.Streams.acquire;
import java.net.URLConnection;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class Processor implements Trace {

    public static final String JSON = "application/json";

    private final URL endpoint;
    protected final Authentication auth;
    protected final Set<Attribute<URLConnection, Payload>> attrs;

    protected Processor(URL endpoint, Authentication auth) {
        attrs = new HashSet<>();
        this.endpoint = endpoint;
        this.auth = auth;
    }

    public URL endpoint() {
        return endpoint;
    }

    public Processor apply(Attribute<?, ?>... attrs) {
        stream(attrs).filter(Objects::nonNull).forEach(this.attrs::add);
        return this;
    }

    public Processor dupe(Attribute<?, ?>... attrs) {
        return new Processor(this.endpoint, this.auth).apply(attrs);
    }

    public Response emit(Payload payload) {
        var body = makeJSON(payload);

        try {
            var remote = useRemote(true);
            remote.setRequestMethod(HttpMethod.POST.name());

            var authString = nonNull(auth) ? auth.yield() : null;
            if (nonNull(authString)) {
                remote.setRequestProperty("Authorization", authString);
            }

            remote.setRequestProperty("Content-Type", JSON);
            remote.setUseCaches(false);

            attrs.forEach(attr -> attr.to(remote, payload));

            try (var output = remote.getOutputStream()) {
                output.write(body.getBytes());
            }

            return new Response(remote.getResponseCode(), Strings.collect(acquire(remote)));
        } catch (IOException ex) {
            warn(ex, "Request failed: {0}", eval(ANGLE_QUOTES, body));
        }

        return new Response();
    }

    protected String makeJSON(Payload payload) {
        return toJSONString(payload);
    }

    protected HttpURLConnection useRemote(boolean useOutput) throws IOException {
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
