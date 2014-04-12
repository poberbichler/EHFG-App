package org.ehfg.app.external.twitter;

import org.ehfg.app.core.repository.TweetRepository;
import org.ehfg.app.core.repository.TwitterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author patrick
 * @since 14.03.2014
 */
@Component
class PersistenceStreamListenerFactory {
	private final TweetRepository tweetRepository;
	private final TwitterUserRepository userRepository;

	@Autowired
	public PersistenceStreamListenerFactory(TweetRepository tweetRepository, TwitterUserRepository userRepository) {
		super();
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
	}

	public PeristenceStreamListener getInstance(String hashtag) {
		return new PeristenceStreamListener(tweetRepository, userRepository, hashtag);
	}
}
