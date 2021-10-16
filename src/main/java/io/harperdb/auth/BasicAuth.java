package io.harperdb.auth;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;
import java.util.Base64;
import java.util.Base64.Encoder;
import static java.util.Objects.nonNull;
import io.harperdb.util.Mention;

public class BasicAuth implements Authentication {

    public static final String SCHEME = "Basic";
    public static final Encoder ENCODER = Base64.getUrlEncoder();

    protected String userName;
    protected String password;

    protected BasicAuth() {}

    @Mention
    public String toCredentials() {
        return format("{0}:{1}", userName, password);
    }

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
        return format("{0} {1}", SCHEME, ENCODER.encodeToString(toCredentials().getBytes(UTF_8)));
    }

    @Override
    public String yield() {
        return this.getAuthString();
    }
}
