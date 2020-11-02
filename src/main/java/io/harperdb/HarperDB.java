package io.harperdb;

import io.harperdb.auth.Authorization;
import io.harperdb.config.HarperDBConfig;

public class HarperDB {

    private final HarperDBConfig config;
    private final Authorization auth;

    private HarperDB(HarperDBConfig config, Authorization auth) {
        this.config = config;
        this.auth = auth;
    }

    public static HarperDB create(HarperDBConfig config, Authorization auth) {
        return new HarperDB(config, auth);
    }

}
