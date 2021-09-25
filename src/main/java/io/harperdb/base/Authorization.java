package io.harperdb.base;

import io.harperdb.auth.BasicAuth;

public interface Authorization {

    public String yield();

    public static BasicAuth basic() {
        return new BasicAuth();
    }
}
