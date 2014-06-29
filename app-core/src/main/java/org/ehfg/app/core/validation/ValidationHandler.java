package org.ehfg.app.core.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 28.06.2014
 */
@Aspect
@Service
class ValidationHandler {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Pointcut("execution(* *(@org.ehfg.app.api.validation.Validate (*)))")
	public void parameterAnnotated() {
		// pointcut has to be empty 
	}
	
	@Pointcut("execution(@org.ehfg.app.api.validation.Validate * *(*))")
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
