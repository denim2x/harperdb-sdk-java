package io.harperdb;

import java.net.http.HttpResponse;
import static java.util.Objects.nonNull;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class Result<S> {
    HttpResponse<S> raw;

    Result(HttpResponse<S> source) {
        this.raw = source;
    }

    public static <S> Result<S> create(HttpResponse<S> source) {
        if (nonNull(source)) {
            return new Result<>(source);
        }

        return null;
    }

    public HttpResponse<S> raw() {
        return raw;
    }
}
