package io.harperdb.util;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
@Retention( RUNTIME )
@Target( { FIELD, METHOD, TYPE } )
public @interface Inspection {
    String value();
}
