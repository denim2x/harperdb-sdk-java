package io.harperdb.test;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class ScenarioTest<STAGE extends Stage<?>> extends com.tngtech.jgiven.testng.ScenarioTest<STAGE, STAGE, STAGE> {

    @Override
    public STAGE when() {
        getScenario().given("").blank();
        return getScenario().when();
    }

}
