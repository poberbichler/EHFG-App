package org.ehfg.app.validation;

import java.lang.annotation.*;

/**
 * Annotation for aspect oriented validation
 * 
 * @author patrick
 * @since 28.06.2014
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface Validate {

}
