package org.ehfg.app.validation;

import org.ehfg.app.base.CoordinateDTO;
import org.ehfg.app.base.LocationDTO;

import javax.validation.*;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * @author patrick
 * @since 08.2015
 */
class LocationDTOValidator implements ConstraintValidator<LocationMappingValid, LocationDTO> {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public void initialize(LocationMappingValid constraintAnnotation) {
        // nothing to do
    }

    @Override
    public boolean isValid(LocationDTO value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.getMappedPointOfInterest() != null) {
            return true;
        }

        Set<ConstraintViolation<CoordinateDTO>> violations = validator.validate(value.getCoordinate(), Default.class);
        if (violations.isEmpty()) {
            return true;
        }

        return false;
    }
}
