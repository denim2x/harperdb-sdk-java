package io.harperdb.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuth implements Auth {

    private String userName = "";
    private String password = "";

    public BasicAuth withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public BasicAuth withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAuthString() {
        String credentials = this.userName + ":" + this.password;
        return "Basic " + Base64.getUrlEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String authorize() {
        return this.getAuthString();
    }
}
