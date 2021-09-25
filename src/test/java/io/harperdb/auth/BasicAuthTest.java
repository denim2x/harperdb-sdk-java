package io.harperdb.auth;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.Format;
import com.tngtech.jgiven.annotation.ScenarioState;
import static io.harperdb.auth.BasicAuth.ENCODER;
import io.harperdb.auth.BasicAuthTest.Steps;
import io.harperdb.base.Authorization;
import io.harperdb.test.Describe;
import io.harperdb.test.GenericFormatter;
import io.harperdb.test.ScenarioTest;
import io.harperdb.test.Stage;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
@Test
class BasicAuthTest extends ScenarioTest<Steps> {

    public void creating_new_BasicAuth() throws Throwable {
        given().new_instance();
        then().getAuthString_should_return(":")
                .and().yield_should_return(":");

        when().withUserName_invoked("user");
        then().getAuthString_should_return("user:")
                .and().yield_should_return("user:");

        when().withPassword_invoked("passwd");
        then().getAuthString_should_return("user:passwd")
                .and().yield_should_return("user:passwd");

        when().withUserName_invoked(null);
        then().getAuthString_should_return("user:passwd")
                .and().yield_should_return("user:passwd");

        when().withPassword_invoked(null);
        then().getAuthString_should_return("user:passwd")
                .and().yield_should_return("user:passwd");
    }

    public static class Steps extends Stage<Steps> {

        static final String _format = "{0} \u00ab{1}\u00bb";
        static final String _scheme = BasicAuth.SCHEME;

        @ScenarioState
        private BasicAuth auth;

        public Steps new_instance() {
            auth = Authorization.basic();
            return this;
        }

        @As("withUserName($) invoked")
        public Steps withUserName_invoked(@Describe String value) {
            auth.withUserName(value);
            return this;
        }

        @As("withPassword($) invoked")
        public Steps withPassword_invoked(@Describe String value) {
            auth.withPassword(value);
            return this;
        }

        @As("getAuthString() should return")
        public Steps getAuthString_should_return(@Describe @Format(value = GenericFormatter.class, args = {_format, _scheme}) String credentials) {
            var expected = getAuthString(credentials);
            assertThat(auth.getAuthString(), is(expected));
            return this;
        }

        @As("yield() should return")
        public Steps yield_should_return(@Describe @Format(value = GenericFormatter.class, args = {_format, _scheme}) String credentials) {
            var expected = getAuthString(credentials);
            assertThat(auth.yield(), is(expected));
            return this;
        }

        String getAuthString(String credentials) {
            return format("{0} {1}", _scheme, ENCODER.encodeToString(credentials.getBytes(UTF_8)));
        }
    }
}
