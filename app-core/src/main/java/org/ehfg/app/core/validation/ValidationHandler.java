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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 28.06.2014
 */
@Aspect
@Service
public class ValidationHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
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
	public void validateParameters(JoinPoint jp) {
		logger.info("i got called, yesyo!");
		
		final Set<ConstraintViolation<Object>> violations = new HashSet<>();
		for (Object argument : jp.getArgs()) {
			violations.addAll(validator.validate(argument, Default.class));
		}
		
		if (!violations.isEmpty()) {
			throw new ValidationException(String.format("%s input parameters are invalid", violations.size()));
		}
	}
}
