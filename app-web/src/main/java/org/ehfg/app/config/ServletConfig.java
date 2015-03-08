package org.ehfg.app.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

/**
 * @author patrick
 * @since 03.2015
 */
public class ServletConfig implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		final AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(WebConfig.class);
		
		servletContext.addListener(new ContextLoaderListener(appContext));
		
		final ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("maintenanceServlet", new DispatcherServlet(appContext));
		dispatcherServlet.setLoadOnStartup(1);
		dispatcherServlet.addMapping("/*");
		
		final ServletRegistration.Dynamic restServlet = servletContext.addServlet("restServlet", new SpringServlet());
		restServlet.setInitParameter("com.sun.jersey.config.property.packages", "org.ehfg.app.rest");
		restServlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
		
		restServlet.setLoadOnStartup(2);
		restServlet.addMapping("/rest/*");
		
		final FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
		springSecurityFilterChain.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
	}
}
