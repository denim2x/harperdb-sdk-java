package io.harperdb.auth;

public final class AuthFactory {

    public static BasicAuthorization basic() {
        return new BasicAuthorization();
    }

}
