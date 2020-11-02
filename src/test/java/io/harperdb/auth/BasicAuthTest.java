package io.harperdb.auth;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicAuthTest {

    @Test
    void basicAuthString() {
        String expected = "Basic " + Base64.getUrlEncoder().encodeToString("user:passwd".getBytes(StandardCharsets.UTF_8));

        String authString = Authorization.basic()
                .withUserName("user")
                .withPassword("passwd")
                .getAuthString();
        assertEquals(expected, authString);
    }

    @Test
    void emptyCredentials() {
        String expected = "Basic " + Base64.getUrlEncoder().encodeToString(":".getBytes(StandardCharsets.UTF_8));

        String authString = Authorization.basic()
                .getAuthString();
        assertEquals(expected, authString);
    }

}