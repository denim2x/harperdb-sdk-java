package io.harperdb.test;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class ScenarioTest<STAGE extends Stage<STAGE>> extends com.tngtech.jgiven.testng.ScenarioTest<STAGE, STAGE, STAGE> {

    public STAGE let() {
        return getScenario().getWhenStage().skip();
    }

    @Override
    public STAGE when() {
        return getScenario().getWhenStage().skip().when();
    }

    @Override
    public STAGE then() {
        return getScenario().getThenStage().skip().then();
    }
}
