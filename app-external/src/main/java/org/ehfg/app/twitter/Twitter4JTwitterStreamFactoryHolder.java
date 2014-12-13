package org.ehfg.app.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * @author patrick  @since 12.2014
 */
@Component
@Profile("!mock")
class Twitter4JTwitterStreamFactoryHolder implements TwitterStreamFactoryHolder {
	private final TwitterStreamFactory factory;

	@Autowired
	public Twitter4JTwitterStreamFactoryHolder(TwitterStreamFactory factory) {
		this.factory = factory;
	}

	@Override
	public TwitterStream getStream() {
		return factory.getInstance();
	}
}
