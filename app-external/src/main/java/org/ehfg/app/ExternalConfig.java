package org.ehfg.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author patrick
 * @since 11.2014
 */
@Configuration
@PropertySource({ "classpath:config/twitter.properties" })
public class ExternalConfig {
	@Autowired
	private Environment environment;

	@Bean
	public TwitterStreamFactory twitterStreamFactory() {
		return new TwitterStreamFactory(twitterConfiguration());
	}

	@Bean
	public twitter4j.conf.Configuration twitterConfiguration() {
		final ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setDebugEnabled(environment.getProperty("twitter.debug.enabled", boolean.class));
		builder.setOAuthConsumerKey(environment.getProperty("twitter.consumer.key"));
		builder.setOAuthConsumerSecret(environment.getProperty("twitter.consumer.secret"));
		builder.setOAuthAccessToken(environment.getProperty("twitter.access.token"));
		builder.setOAuthAccessTokenSecret(environment.getProperty("twitter.access.secret"));
		builder.setUseSSL(environment.getProperty("twitter.use.ssl", boolean.class));

		return builder.build();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}