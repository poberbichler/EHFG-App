package org.ehfg.app;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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
public @interface MockService {

}
