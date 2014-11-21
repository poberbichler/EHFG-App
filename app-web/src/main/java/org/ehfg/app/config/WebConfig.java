package org.ehfg.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author patrick
 * @since 11.2014
 */
@ImportResource({"classpath:META-INF\\spring\\spring.base.xml",
	"classpath:META-INF\\spring\\spring.persistence.xml",
	"classpath:META-INF\\spring\\spring.rest.xml",
	"classpath:META-INF\\spring\\spring.twitter.xml"})
@ComponentScan(basePackages = "org.ehfg.app")
public class WebConfig extends WebMvcConfigurationSupport {

}
