package io.harperdb.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthorization implements Authorization {

    private String userName = "";
    private String password = "";

    public BasicAuthorization withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public BasicAuthorization withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getAuthString() {
        String credentials = this.userName + ":" + this.password;
        return "Basic " + Base64.getUrlEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
