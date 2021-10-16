package io.harperdb;

import java.net.HttpURLConnection;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class Response {

    protected final int statusCode;
    protected final String content;

    protected Response() {
        statusCode = HttpURLConnection.HTTP_NO_CONTENT;
        content = null;
    }

    protected Response(int statusCode, CharSequence content) {
        this.statusCode = statusCode;
        this.content = content.toString();
    }

    public int statusCode() {
        return statusCode;
    }

    public String content() {
        return content;
    }

}
