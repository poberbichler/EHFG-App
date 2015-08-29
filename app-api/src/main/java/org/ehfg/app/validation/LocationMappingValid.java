package org.ehfg.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author patrick
 * @since 08.2015
 */
@Documented
@Target(ElementType.TYPE)
@Constraint(validatedBy = LocationDTOValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface LocationMappingValid {
    String message() default "either a coordinate or a point of interest has to be given";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
