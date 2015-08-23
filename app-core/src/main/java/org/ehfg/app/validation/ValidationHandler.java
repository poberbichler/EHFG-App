package org.ehfg.app.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashSet;
import java.util.Set;

/**
 * @author patrick
 * @since 28.06.2014
 */
@Aspect
@Service
final class ValidationHandler {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Pointcut("execution(* *(@org.ehfg.app.validation.Validate (*)))")
	public void parameterAnnotated() {
		// pointcut has to be empty 
	}
	
	@Pointcut("execution(@org.ehfg.app.validation.Validate * *(*))")
	public void methodAnnotated() {
		// pointcut has to be empty
	}
	
	@Before("methodAnnotated() or parameterAnnotated()")
	public void validateParameters(JoinPoint joinPoint) {
		final Set<ConstraintViolation<Object>> violations = new HashSet<>();
		for (Object argument : joinPoint.getArgs()) {
			violations.addAll(validator.validate(argument, Default.class));
		}
		
		if (!violations.isEmpty()) {
			throw new ValidationException(String.format("%s input parameters are invalid", violations.size()));
		}
	}
}
