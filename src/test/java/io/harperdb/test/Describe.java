package io.harperdb.test;

import com.tngtech.jgiven.annotation.Format;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
@Format(GenericFormatter.class)
@Retention(RUNTIME)
@Target({PARAMETER, ANNOTATION_TYPE, FIELD})
public @interface Describe {
    public String[] args() default {};
}
