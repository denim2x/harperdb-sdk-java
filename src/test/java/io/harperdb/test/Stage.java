package io.harperdb.test;

import com.tngtech.jgiven.annotation.As;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class Stage<SELF extends com.tngtech.jgiven.Stage<?>> extends com.tngtech.jgiven.Stage<SELF> {

        @As("")
        public SELF blank() {
            return self();
        }

}
