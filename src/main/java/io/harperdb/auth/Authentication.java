package io.harperdb.auth;

@FunctionalInterface
public interface Authentication {

    String yield();

    public static BasicAuth basic() {
        return new BasicAuth();
    }
}
