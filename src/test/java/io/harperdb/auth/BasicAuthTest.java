package io.harperdb.auth;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ScenarioState;
import static io.harperdb.auth.BasicAuth.ENCODER;
import static io.harperdb.auth.BasicAuth.SCHEME;
import io.harperdb.auth.BasicAuthTest.Steps;
import io.harperdb.test.Describe;
import io.harperdb.test.ScenarioTest;
import io.harperdb.test.Stage;
import static java.nio.charset.StandardCharsets.UTF_8;

import org.testng.annotations.Test;
import static io.harperdb.test.Redone.redo;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
@Test
class BasicAuthTest extends ScenarioTest<Steps> {

    static Object with(String text) {
        var value = ENCODER.encodeToString(text.getBytes(UTF_8));
        return redo("{0} {1}", SCHEME, value).as("\"{0} \u25e7{1}\u25e8\"", SCHEME, text);
    }

    public void instantiating() throws Throwable {
        given().new_instance()
                .then().getAuthString().returns(with(":"))
                .and().yield().returns(with(":"));

        then().withUserName("user").succeeds()
                .and().getAuthString().returns(with("user:"))
                .and().yield().returns(with("user:"));

        then().withPassword("passwd").succeeds()
                .and().getAuthString().returns(with("user:passwd"))
                .and().yield().returns(with("user:passwd"));

        then().withUserName(null).succeeds()
                .and().getAuthString().returns(with("user:passwd"))
                .and().yield().returns(with("user:passwd"));

        then().withPassword(null).succeeds()
                .and().getAuthString().returns(with("user:passwd"))
                .and().yield().returns(with("user:passwd"));
    }

    public static class Steps extends Stage<Steps> {

        @ScenarioState
        private BasicAuth auth;

        public Steps new_instance() {
            auth = Authentication.basic();
            return this;
        }

        @As("withUserName($)")
        public Steps withUserName(@Describe String value) {
            actual = auth.withUserName(value);
            return this;
        }

        @As("withPassword($)")
        public Steps withPassword(@Describe String value) {
            actual = auth.withPassword(value);
            return this;
        }

        @As("getAuthString()")
        public Steps getAuthString() {
            actual = auth.getAuthString();
            return this;
        }

        @As("yield()")
        public Steps yield() {
            actual = auth.yield();
            return this;
        }
    }
}
