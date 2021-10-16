package io.harperdb.auth;

public interface Authentication {

    String yield();

    public static BasicAuth basic() {
        return new BasicAuth();
    }
}
