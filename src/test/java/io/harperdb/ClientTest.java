package io.harperdb;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ScenarioState;
import io.harperdb.ClientTest.Steps;
import io.harperdb.test.Describe;
import io.harperdb.test.ScenarioTest;
import io.harperdb.test.Stage;
import java.net.URI;
import java.net.URL;
import org.testng.annotations.Test;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
@Test
class ClientTest extends ScenarioTest<Steps> {

    static URL endpoint() throws Throwable {
        return new URL("http://foobar:1234");
    }

    public void HarperDB() throws Throwable {
        var endpointA = endpoint();
        let().open(endpointA)
                .then().endpoint().returns(endpointA);

        URL endpointB = null;
        let().open(endpointB).fails(IllegalArgumentException.class);

        URL endpointC = new URL("http://foobaz:3456");
        let().open(endpointC.toURI())
                .then().endpoint().returns(endpointC);

        URI endpointD = null;
        let().open(endpointD).fails(IllegalArgumentException.class);

        URL endpointE = new URL("http://fooqux:5678");
        let().open(endpointE.toString())
                .then().endpoint().returns(endpointE);

        String endpointF = null;
        let().open(endpointF).fails(IllegalArgumentException.class);

        String endpointG = "";
        let().open(endpointG).fails(IllegalArgumentException.class);

        let().open(endpointA.getHost(), endpointA.getPort())
                .then().endpoint().returns(endpointA);

        let().open(null, 1234).fails(IllegalArgumentException.class);

        var endpointH = new URL("https://foobar:1234");
        let().open("https", endpointH.getHost(), endpointH.getPort())
                .then().endpoint().returns(endpointH);

        let().open(null, endpointA.getHost(), endpointA.getPort())
                .then().endpoint().returns(endpointA);

        let().open(null, null, 1234).fails(IllegalArgumentException.class);
    }

    static class Steps extends Stage<Steps> {

        @ScenarioState
        private HarperDB instance;

        @As("open($)")
        public Steps open(@Describe URL endpoint) {
            try {
                instance = HarperDB.open(endpoint);
                exception = null;
            } catch (IllegalArgumentException ex) {
                instance = null;
                exception = ex;
            }

            return this;
        }

        @As("open($)")
        public Steps open(@Describe URI endpoint) {
            try {
                instance = HarperDB.open(endpoint);
                exception = null;
            } catch (IllegalArgumentException ex) {
                instance = null;
                exception = ex;
            }

            return this;
        }

        @As("open($)")
        public Steps open(@Describe String endpoint) {
            try {
                instance = HarperDB.open(endpoint);
                exception = null;
            } catch (IllegalArgumentException ex) {
                instance = null;
                exception = ex;
            }

            return this;
        }

        @As("open(host $, port $)")
        public Steps open(@Describe String host, @Describe int port) {
            try {
                instance = HarperDB.open(host, port);
                exception = null;
            } catch (IllegalArgumentException ex) {
                instance = null;
                exception = ex;
            }

            return this;
        }

        @As("open(protocol $, host $, port $)")
        public Steps open(@Describe String protocol, @Describe String host, @Describe int port) {
            try {
                instance = HarperDB.open(protocol, host, port);
                exception = null;
            } catch (IllegalArgumentException ex) {
                instance = null;
                exception = ex;
            }

            return this;
        }

        @As("endpoint()")
        public Steps endpoint() {
            actual = instance.endpoint();
            exception = null;
            return this;
        }

        @As("authorization()")
        public Steps authorization() {
            actual = instance.authorization();
            exception = null;
            return this;
        }

        @As("client()")
        public Steps client() {
            actual = instance.client();
            exception = null;
            return this;
        }

    }
}
