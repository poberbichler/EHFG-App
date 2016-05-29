package org.ehfg.app.config;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.ehfg.app.converter.LocalDateTimeToUTCTimestampSerializer;
import org.ehfg.app.converter.LocalDateToStringConverter;
import org.ehfg.app.converter.LongToLocalDateTimeConverter;
import org.ehfg.app.converter.StringToLocalDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author patrick
 * @since 11.2014
 */
@EnableWebMvc
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
@PropertySource(ignoreResourceNotFound = true,
        value = {"classpath:config.properties", "file:////${user.home}/ehfg.properties"})
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private Environment environment;

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() throws UnknownHostException {
        final TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(environment.getProperty("server.port", Integer.class));
        factory.setAddress(InetAddress.getByName(environment.getProperty("server.address")));
        return factory;
    }

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
    public ViewResolver viewResolver() {
        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setExcludedViewNames(new String[]{"tweetExport"});

        return viewResolver;
    }

    @Bean
    public ViewResolver beanNameViewResolver() {
        final BeanNameViewResolver resolver = new BeanNameViewResolver();
        resolver.setOrder(2);
        return resolver;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateConverter());
        registry.addConverter(new LocalDateToStringConverter());
        registry.addConverter(new LongToLocalDateTimeConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        final String mobilePath = environment.getProperty("absolute.mobile.path");
        if (mobilePath != null) {
            registry.addResourceHandler("/mobile/**").addResourceLocations(mobilePath);
        }
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true).modulesToInstall(new JaxbAnnotationModule());
        builder.serializerByType(LocalDateTime.class, new LocalDateTimeToUTCTimestampSerializer());

        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        converters.add(new StringHttpMessageConverter());
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("versions");
        return messageSource;
    }
}
