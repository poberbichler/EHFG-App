package org.ehfg.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import java.util.Arrays;

/**
 * @author patrick
 * @since 11.2014
 */
@EnableSocial
@EnableCaching
@Configuration
@PropertySource({ "classpath:config/twitter.properties" })
public class ExternalConfig implements SocialConfigurer {
	@Autowired
	private Environment environment;

	@Bean
	public CacheManager cacheManager() {
		final SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("speaker"), new ConcurrentMapCache("session")));

		return cacheManager;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public Twitter twitter() {
		return new TwitterTemplate(
				environment.getProperty("twitter.consumer.key"),
				environment.getProperty("twitter.consumer.secret"), 
				environment.getProperty("twitter.access.token"),
				environment.getProperty("twitter.access.secret"));
	}

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
		configurer.addConnectionFactory(new TwitterConnectionFactory(
				environment.getProperty("twitter.consumer.key"), 
				environment.getProperty("twitter.consumer.secret")));
	}

	@Override
	public UserIdSource getUserIdSource() {
		return () -> "EHFG_APP";
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator factoryLocator) {
		return new InMemoryUsersConnectionRepository(factoryLocator);
	}
}
