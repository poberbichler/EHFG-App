package org.ehfg.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Starter class that bootstraps the servlet container, and starts the ehfg backend
 *
 * @author patrick
 * @since 05.2015
 */
@SpringBootApplication
public class AppStarter extends SpringBootServletInitializer {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppStarter.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppStarter.class);
	}
}
