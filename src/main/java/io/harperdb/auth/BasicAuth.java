package io.harperdb.auth;

import io.harperdb.base.Authorization;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;
import java.util.Base64;
import java.util.Base64.Encoder;
import static java.util.Objects.nonNull;
import io.harperdb.util.Inspection;

@Inspection("{userName}:{password}")
public class BasicAuth implements Authorization {

    public static final String SCHEME = "Basic";
    public static final Encoder ENCODER = Base64.getUrlEncoder();

    protected String userName = "";
    protected String password = "";

    public BasicAuth withUserName(String userName) {
        if (nonNull(userName)) {
            this.userName = userName;
        }
        return this;
    }

    public BasicAuth withPassword(String password) {
        if (nonNull(password)) {
            this.password = password;
        }
        return this;
    }

    public String userName() {
        return userName;
    }

    public String password() {
        return password;
    }

    public String getAuthString() {
        var credentials = format("{0}:{1}", userName, password);
        return format("{0} {1}", SCHEME, ENCODER.encodeToString(credentials.getBytes(UTF_8)));
    }

    @Override
    public String yield() {
        return this.getAuthString();
    }
}
