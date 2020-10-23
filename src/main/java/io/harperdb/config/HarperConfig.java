package io.harperdb.config;

public class HarperConfig {

    private HarperConfig() {
    }

    public static HarperConfig create() {
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    private static class Builder {
    }
}
