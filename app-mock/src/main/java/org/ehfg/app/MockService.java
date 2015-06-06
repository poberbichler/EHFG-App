package org.ehfg.app;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Convenience annotation for mocking services
 *
 * @author patrick
 * @since 06.2015
 */
@Service
@Profile("mock")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MockService {
	// empty
}
