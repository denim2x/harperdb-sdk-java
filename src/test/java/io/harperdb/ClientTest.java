package io.harperdb;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ScenarioState;
import io.harperdb.ClientTest.Steps;
import io.harperdb.base.Authorization;
import io.harperdb.test.Describe;
import io.harperdb.test.ScenarioTest;
import io.harperdb.test.Stage;
import java.net.URI;
import static java.text.MessageFormat.format;
import java.time.Duration;
import static java.util.Objects.isNull;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
@Test
public class ClientTest extends ScenarioTest<Steps> {

    static final URI ENDPOINT = URI.create(format("http://localhost:{0,number,#}", HarperDB.PORT));

    public void creating_new_HarperDB() throws Throwable {
        given().new_instance();
        then()
                .endpoint(ENDPOINT)
                .and().authorization(null)
                .and().timeout(HarperDB.TIMEOUT);

        var endpointA = "http://foobar:1234";
        when().useEndpoint().called(endpointA);
        then().endpoint(URI.create(endpointA));

        String endpointB = null;
        when().useEndpoint().called(endpointB);
        then().endpoint(URI.create(endpointA)).comment("as previous");

        var endpointC = URI.create("http://foobaz:3456");
        when().useEndpoint().called(endpointC);
        then().endpoint(endpointC);

        URI endpointD = null;
        when().useEndpoint().called(endpointD);
        then().endpoint(endpointC).comment("as previous");

        var endpointE = URI.create("http://fooqux:5678");
        when().useEndpoint().called(endpointE.getHost(), endpointE.getPort());
        then().endpoint(endpointE);

        when().endpoint_with().called("https");
        then().endpoint(URI.create("https://fooqux:5678"));

        String endpointF = "";
        when().useEndpoint().called(endpointF).comment("not valid");
        then().endpoint(IllegalStateException.class);

        var authA = Authorization.basic().withUserName("user").withPassword("passwd");
        when().withAuthorization().called(authA);
        then().authorization(authA);

        when().withAuthorization().called(null);
        then().authorization(null);

        var timeoutA = 20;
        when().withTimeout().called(timeoutA);
        then().timeout(Duration.ofSeconds(timeoutA));

        var timeoutB = Duration.ofSeconds(30);
        when().withTimeout().called(timeoutB);
        then().timeout(timeoutB);

        when().withTimeout().called(null);
        then().timeout(timeoutB).comment("as previous");
    }

    public void producing_the_Session() throws Throwable {
        given().new_instance().with().;
    }

    public static class Steps extends Stage<Steps> {

        @ScenarioState
        private HarperDB client;

        @ScenarioState
        private HarperDB.Endpoint endpoint;

        public Steps new_instance() {
            client = new HarperDB();
            return this;
        }

        @As("endpoint()")
        public Steps endpoint(@Describe URI expected) {
            assertThat(client.endpoint(), isA(URI.class));
            assertThat(client.endpoint(), equalTo(expected));
            return this;
        }

        @As("endpoint()")
        public Steps endpoint(@Describe Class<? extends Throwable> errorClass) {
            Throwable error = null;
            try {
                client.endpoint();
            } catch (Throwable ex) {
                error = ex;
            }

            assertThat(error, isA(errorClass));
            return this;
        }

        @As("authorization()")
        public Steps authorization(@Describe Authorization expected) {
            if (isNull(expected)) {
                assertThat(client.authorization(), nullValue(Authorization.class));
            } else {
                assertThat(client.authorization(), isA(Authorization.class));
                assertThat(client.authorization(), is(expected));
            }

            return this;
        }

        @As("timeout()")
        public Steps timeout(@Describe Duration expected) {
            assertThat(client.timeout(), isA(Duration.class));
            assertThat(client.timeout(), equalTo(expected));
            return this;
        }

        @As("useEndpoint($)")
        public Steps useEndpoint(@Describe URI endpoint) {
            this.endpoint = client.useEndpoint(endpoint);
            return this;
        }

        @As("useEndpoint($)")
        public Steps useEndpoint(@Describe String endpoint) {
            this.endpoint = client.useEndpoint(endpoint);
            return this;
        }

        @As("useEndpoint(host $, port $)")
        public Steps useEndpoint(@Describe String host, @Describe int port) {
            this.endpoint = client.useEndpoint(host, port);
            return this;
        }

        @As("endpoint.with($)")
        public Steps endpoint_with(@Describe String scheme) {
            endpoint.with(scheme);
            return this;
        }

        @As("withAuthorization($)")
        public Steps withAuthorization(@Describe Authorization auth) {
            client.withAuthorization(auth);
            return this;
        }

        @As("withTimeout($)")
        public Steps withTimeout(@Describe int timeout) {
            client.withTimeout(timeout);
            return this;
        }

        @As("withTimeout($)")
        public Steps withTimeout(@Describe Duration timeout) {
            client.withTimeout(timeout);
            return this;
        }
    }
}
