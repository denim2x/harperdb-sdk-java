package io.harperdb.config;

public class ConfigParserException extends RuntimeException {

    public ConfigParserException() {
    }

    public ConfigParserException(String message) {
        super(message);
    }

    public ConfigParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigParserException(Throwable cause) {
        super(cause);
    }

    public ConfigParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
