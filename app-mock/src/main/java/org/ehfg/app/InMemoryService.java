package org.ehfg.app;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Convenience annotation for mocking services.<br>
 * Basically combines {@link Service} and {@link Profile @Profile("in-memory-db")}
 * 
 * @author patrick
 * @since 12.2014
 */
@Service
@Profile("in-memory-db")
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InMemoryService {
	// empty
}
