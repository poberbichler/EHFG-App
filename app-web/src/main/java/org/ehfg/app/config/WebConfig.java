package org.ehfg.app.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.ehfg.app.converter.LocalDateToStringConverter;
import org.ehfg.app.converter.StringToLocalDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**
 * @author patrick
 * @since 11.2014
 */
@EnableWebMvc
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "org.ehfg.app")
@PropertySource(ignoreResourceNotFound = true, 
		value = { "classpath:config.properties", "file:////${user.home}/ehfg.properties" })
@Import(EmbeddedServletContainerAutoConfiguration.class)
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private Environment environment;

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		dispatcherServlet.setContextClass(EmbeddedWebApplicationContext.class);

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet);
		servletRegistrationBean.addUrlMappings("/*");
		servletRegistrationBean.setName("maintenanceServlet");
		return servletRegistrationBean;
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setPrefix("classpath:templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setCacheable(false);

		return resolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.addDialect(new LayoutDialect());

		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver viewResolver() {
		final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());

		return viewResolver;
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToLocalDateConverter());
		registry.addConverter(new LocalDateToStringConverter());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		
		final String mobilePath = environment.getProperty("absolute.mobile.path");
		if (mobilePath != null) {
			registry.addResourceHandler("/mobile/**").addResourceLocations(mobilePath);
		}
	}
}
