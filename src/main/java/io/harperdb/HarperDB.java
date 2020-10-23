package io.harperdb;

import io.harperdb.auth.Authorization;
import io.harperdb.config.HarperConfig;

public class HarperDB {

    private final HarperConfig config;
    private final Authorization auth;

    private HarperDB(HarperConfig config, Authorization auth) {
        this.config = config;
        this.auth = auth;
    }

    public static HarperDB create(HarperConfig config, Authorization auth) {
        return new HarperDB(config, auth);
    }

}
