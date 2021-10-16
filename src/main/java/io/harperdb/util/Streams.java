package io.harperdb.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface Streams {

    public static InputStream acquire(HttpURLConnection remote) throws IOException {
        if (remote.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return remote.getInputStream();
        }

        return remote.getErrorStream();
    }
}
