package io.harperdb.test;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.IntroWord;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.annotation.StepComment;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class Stage<SELF extends com.tngtech.jgiven.Stage<?>> extends com.tngtech.jgiven.Stage<SELF> {

    @ScenarioState
    protected Object actual;

    @ScenarioState
    protected Throwable exception;

    @IntroWord
    @As("\n")
    public SELF skip() {
        return self();
    }

    @As("should succeed")
    public SELF succeeds() {
        assertThat(exception, nullValue());
        return self();
    }

    @As("should fail with")
    public SELF fails(@Describe Class<? extends Throwable> errorClass) {
        assertThat(exception, isA(errorClass));
        return self();
    }

    @As("should return")
    public SELF returns(@Describe Object expected) {
        assertThat(expected, equalTo(actual));
        return self();
    }

    @As("should return")
    public SELF returns_same(@Describe Object expected) {
        assertThat(expected, sameInstance(actual));
        return self();
    }

    @StepComment
    public SELF as(String comment) {
        return self();
    }

}
